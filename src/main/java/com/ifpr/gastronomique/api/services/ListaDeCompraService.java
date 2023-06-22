package com.ifpr.gastronomique.api.services;

import java.io.IOException;
import java.text.DecimalFormat;
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
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
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
			    itemListaDeCompra.setValor(item.getValorTotal());
			    itemListaDeCompra.setListaDeCompra(listaDeCompra);
			    listaDeCompra.addItensDaListaDeCompra(itemListaDeCompra);
	    	} else {
	    		itemListaDeCompra.setValor(itemListaDeCompra.getValor() + item.getValorTotal());
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
		String nomeArquivo = listaDeCompra.getDataCriacao().toString();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + nomeArquivo + ".pdf\"");
        
        // Criação do documento PDF
        Document document = new Document();
        
        try {
        	PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            
            String caminhoImagem = "src/main/resources/images/ifpr.png";
            Image logoIfpr = Image.getInstance(caminhoImagem);
            logoIfpr.setAbsolutePosition(0, 770); 
            logoIfpr.scaleAbsoluteHeight(60);
            logoIfpr.scaleAbsoluteWidth(60);
            
            BaseColor corCabecalhosColunas = new BaseColor(47, 158, 64);
            BaseColor corConteudoTabela = new BaseColor(238,238,238);

            // Criando os cabeçalhos
            DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            PdfPTable tabela = new PdfPTable(4);
            PdfPCell cabecalho = new PdfPCell(new Paragraph(""));
            Font fonteNegrito = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
            cabecalho.setPhrase(new Paragraph("Data da lista: " + listaDeCompra.getDataCriacao().format(formatoData), fonteNegrito));
            cabecalho.setColspan(4);
            cabecalho.setBackgroundColor(corCabecalhosColunas);
            cabecalho.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabela.addCell(cabecalho);
            
            // Criando Aulas
            PdfPCell aulasUtilizadasPdf = new PdfPCell(new Paragraph(""));
            aulasUtilizadasPdf.setPhrase(new Paragraph("Aulas que solicitaram os insumos", fonteNegrito));
            aulasUtilizadasPdf.setColspan(4);
            aulasUtilizadasPdf.setBackgroundColor(corCabecalhosColunas);
            aulasUtilizadasPdf.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabela.addCell(aulasUtilizadasPdf);
            
            //Mostrando data e nome das aulas
            Font fonte = new Font(Font.FontFamily.HELVETICA, 9);
            for(Aula aula : listaDeAulas) {
            	PdfPCell celulaAulaData = new PdfPCell(new Phrase(aula.getDataUtilizacao().format(formatoData), fonte));
                celulaAulaData.setColspan(1);
                celulaAulaData.setHorizontalAlignment(Element.ALIGN_CENTER);
            	celulaAulaData.setBackgroundColor(corConteudoTabela);
                tabela.addCell(celulaAulaData);
                
                PdfPCell celulaAulaDescricao = new PdfPCell(new Phrase(aula.getDescricao(), fonte));
                celulaAulaDescricao.setColspan(3);
                celulaAulaDescricao.setHorizontalAlignment(Element.ALIGN_CENTER);
                celulaAulaDescricao.setBackgroundColor(corConteudoTabela);
                tabela.addCell(celulaAulaDescricao);
            }
            
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome do insumo", fonteNegrito));
			col1.setHorizontalAlignment(Element.ALIGN_CENTER);
			col1.setBackgroundColor(corCabecalhosColunas);
			
			PdfPCell col2 = new PdfPCell(new Paragraph("Unidade de medida", fonteNegrito));
			col2.setHorizontalAlignment(Element.ALIGN_CENTER);
			col2.setBackgroundColor(corCabecalhosColunas);
			
			PdfPCell col3 = new PdfPCell(new Paragraph("Quantidade", fonteNegrito));
			col3.setHorizontalAlignment(Element.ALIGN_CENTER);
			col3.setBackgroundColor(corCabecalhosColunas);
			
			PdfPCell col4 = new PdfPCell(new Paragraph("Valor", fonteNegrito));
			col4.setHorizontalAlignment(Element.ALIGN_CENTER);
			col4.setBackgroundColor(corCabecalhosColunas);
			
			tabela.addCell(col1);
            tabela.addCell(col2);
            tabela.addCell(col3);
            tabela.addCell(col4);
            
            // Lista de Insumos
            Double valorTotalDosInsumosDaLista = 0.0;
            for(ItemListaDeCompra item : listaDeCompra.getItensDaListaDeCompra()) {
            	
            	valorTotalDosInsumosDaLista += item.getValor();
            	
            	PdfPCell nomeInsumo = new PdfPCell(new Paragraph(item.getInsumo().getDenominacao(), fonte));
            	nomeInsumo.setHorizontalAlignment(Element.ALIGN_CENTER);
            	nomeInsumo.setBackgroundColor(corConteudoTabela);
            	tabela.addCell(nomeInsumo);
            	
            	PdfPCell unidadeDeMedida = new PdfPCell(new Paragraph(item.getInsumo().getUnidadeDeMedida().toString(), fonte));
            	unidadeDeMedida.setHorizontalAlignment(Element.ALIGN_CENTER);
            	unidadeDeMedida.setBackgroundColor(corConteudoTabela);
            	tabela.addCell(unidadeDeMedida);
            	
            	String quantidadeStr = new DecimalFormat("0.000").format(item.getQuantidade());
            	PdfPCell quantidade = new PdfPCell(new Paragraph(quantidadeStr.replace(".", ","), fonte));
            	quantidade.setHorizontalAlignment(Element.ALIGN_CENTER);
            	quantidade.setBackgroundColor(corConteudoTabela);
            	tabela.addCell(quantidade);
            	
            	DecimalFormat formatoValor = new DecimalFormat("R$ 0.00");
            	String valorFormatado = formatoValor.format(item.getValor());
            	PdfPCell valorCell = new PdfPCell(new Paragraph(valorFormatado, fonte));
            	valorCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	valorCell.setBackgroundColor(corConteudoTabela);
            	tabela.addCell(valorCell);
            }
            
            PdfPCell valorTotalInsumos = new PdfPCell(new Paragraph(""));
            valorTotalInsumos.setPhrase(new Paragraph("Valor total dos insumos solicitados", fonteNegrito));
            valorTotalInsumos.setColspan(4);
            valorTotalInsumos.setBackgroundColor(corCabecalhosColunas);
            valorTotalInsumos.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabela.addCell(valorTotalInsumos);
            
            DecimalFormat formatoValor = new DecimalFormat("R$ 0.00");
        	String valorFormatado = formatoValor.format(valorTotalDosInsumosDaLista);
        	PdfPCell valorTotalCell = new PdfPCell(new Paragraph(valorFormatado, fonteNegrito));
        	valorTotalCell.setColspan(4);
        	valorTotalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	valorTotalCell.setBackgroundColor(corConteudoTabela);
        	tabela.addCell(valorTotalCell);
            
            document.add(logoIfpr);
            document.add(tabela);
            document.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}