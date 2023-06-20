package com.ifpr.gastronomique.api.services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifpr.gastronomique.api.enums.StatusAulaEnum;
import com.ifpr.gastronomique.api.models.Aula;
import com.ifpr.gastronomique.api.models.ItemAula;
import com.ifpr.gastronomique.api.models.ItemListaDeCompra;
import com.ifpr.gastronomique.api.models.ListaDeCompra;
import com.ifpr.gastronomique.api.repositories.AulaRepository;
import com.ifpr.gastronomique.api.repositories.InsumoRepository;
import com.ifpr.gastronomique.api.repositories.ItemListaDeCompraRepository;
import com.ifpr.gastronomique.api.repositories.ListaDeCompraRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ListaDeCompraService {

	@Autowired
	private ListaDeCompraRepository repository;
	
	@Autowired
	private AulaRepository aulaRepository;
	
	@Autowired
	private ListaDeCompraRepository listaDeCompraRepository;
	
	@Autowired
	ItemListaDeCompraRepository itemListaDeCompraRepository;
	
	@Autowired 
	InsumoRepository insumoRepository;
	
	public List<ListaDeCompra> listarTodasListasDeCompra() {
		return repository.findAll();
	}
	
	//IMPLEMENTAR O QUANTO ANTES(TA ACABANDO O TEMPO)
	public ResponseEntity<ListaDeCompra> salvarListaDeCompra(List<Long> listaDeAulasId) {
		
		//Cria lista de Aulas
		List<Aula> listaDeAulas = new ArrayList<>();
		
		// Cria lista de itens das aulas vazia
		List<ItemAula> listaDeItensAula = new ArrayList<>();
	    
	    //Popula os itens da listaDeItensAula
	    for (Long idAula : listaDeAulasId) {
			Aula aula = aulaRepository.findById(idAula).orElse(null);
			listaDeAulas.add(aula);
			if(aula != null) {
				for(ItemAula item: aula.getItensAula()) {
					listaDeItensAula.add(item);
				}
			}
		}
	    
	    //Cria uma nova lista de compra
	    ListaDeCompra listaDeCompra = new ListaDeCompra();
	    listaDeCompra.setDataCriacao(LocalDate.now());

	    //Adiciona itens da aula a listaDeCompra
	    for(ItemAula item : listaDeItensAula) {
	    	ItemListaDeCompra itemListaDeCompra = 
	    			listaDeCompra.existeInsumoNaListaDeCompra(item.getInsumo().getId());
	    	
	    	if(itemListaDeCompra == null) { 
		    	itemListaDeCompra = new ItemListaDeCompra();
			    itemListaDeCompra.setInsumo(item.getInsumo());
			    itemListaDeCompra.setQuantidade(item.getQuantidade());
			    itemListaDeCompra.setListaDeCompra(listaDeCompra);
			    listaDeCompra.addItensDaListaDeCompra(itemListaDeCompra);
	    	} else {
	    		itemListaDeCompra.setQuantidade(itemListaDeCompra.getQuantidade() + item.getQuantidade());
	    }	}    
	    
	    // Persistir os dados da lista de compra
	    listaDeCompra = listaDeCompraRepository.save(listaDeCompra);
	    
	    //Atualizar as aulas informando em qual lista de compras foram utilizadas
	    for (Aula aula : listaDeAulas) {
	    	aula.setStatus(StatusAulaEnum.FINALIZADA);
			aula.setListaDeCompra(listaDeCompra);
			aulaRepository.save(aula);
		}
	        
	    //return new ResponseEntity<ListaDeCompra>(listaDeCompra, HttpStatus.OK);
	    return ResponseEntity.status(HttpStatus.OK).body(listaDeCompra);
	}
	
	public void gerarPdfListaDeCompra(HttpServletResponse response, Long idListaDeCompra) throws IOException {
        
		ListaDeCompra listaDeCompra = listaDeCompraRepository.findById(idListaDeCompra).orElse(null);
		List<Aula> listaDeAulas = aulaRepository.buscarAulasPorListaDeCompraId(listaDeCompra.getId());
		
		// Configuração do cabeçalho da resposta
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"example.pdf\"");
        
        // Criação do documento PDF
        Document document = new Document();
        
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Adiciona conteúdo ao documento
            DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            PdfPTable tabela = new PdfPTable(3);
            PdfPCell cabecalho = new PdfPCell(new Paragraph("DATA: " + listaDeCompra.getDataCriacao().format(formatoData)));
            cabecalho.setColspan(3);
            tabela.addCell(cabecalho);
            
            PdfPCell aulasUtilizadasPdf = new PdfPCell(new Paragraph("Aulas Utilizadas: "));
            aulasUtilizadasPdf.setColspan(3);
            tabela.addCell(aulasUtilizadasPdf);
            String aulas = "";
            for(Aula a : listaDeAulas) {
            	if(aulas.isEmpty())
            		aulas += a.getDescricao();
            	else
            		aulas +=  ", "  + a.getDescricao();
            }
            PdfPCell celulaAula = new PdfPCell(new Phrase(aulas));
            celulaAula.setColspan(3);
            tabela.addCell(celulaAula);
            
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome Do Insumo"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Unidade De Medida"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Quantidade"));
            tabela.addCell(col1);
            tabela.addCell(col2);
            tabela.addCell(col3);
			
            for(ItemListaDeCompra item : listaDeCompra.getItensDaListaDeCompra()) {
            	tabela.addCell(item.getInsumo().getDenominacao());
            	tabela.addCell(item.getInsumo().getUnidadeDeMedida().toString());
            	tabela.addCell(item.getQuantidade().toString());
            }
            
            
            document.add(tabela);
            document.close();
            
        } catch (Exception e) {
            // Trate exceções conforme necessário
            e.printStackTrace();
        }
    }

}