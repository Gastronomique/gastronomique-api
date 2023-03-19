# gastronomique-api

API feita com SpringBoot responsável por tratar requisições e seder os dados ao frontend grastronomique-front

No Curso de Gastronomia do IFPR - Instituto Federal Do Paraná, campus de Foz do Iguaçu - PR, cada professor precisa fazer uma requisição de insumos (produtos) que serão utilizados em suas aulas com uma antecedência de 10 dias, para que sejam separados os materiais que serão utilizados no dia da aula. O Processo atual é feito manualmente, consiste em juntar essas listas, agrupar todos os itens e quantidades em uma lista principal. Feito isso, é comparado os itens desta lista principal com os itens que possuem em estoque, caso não estejam disponíveis os insumos solicitados, é gerado uma nova lista com os itens que devem ser comprados e entregues por um determinado fornecedor.

Ao receber os insumos do fornecedor, os itens são adicionados ao estoque, e em seguida são separados os itens específicos para cada aula com base nas requisições feitas pelos professores. Visto isso, torna-se um processo muito moroso e manual, no qual são utilizadas planilhas para um certo tipo de controle.

O sistema de gerenciamento de estoque foi criado para facilitar a geração da lista de compras e simplificar o processo de separação dos insumos utilizados nas aulas. Ele irá permitir que o professor faça a requisição de insumos através de uma interface simplificada, após isso, os itens solicitados aparecerão em formato de uma lista de aulas para o administrador. Com base nisso, o administrador irá selecionar um período e gerará a requisição de compras final.

Além disso, o sistema de gerenciamento de estoque automatiza a junção das listas de aulas solicitadas pelos professores em um período determinado. Ele irá comparar os itens disponíveis no estoque e gerará uma lista de compras com a quantidade necessária para completar as aulas. Também criará uma lista dos itens disponíveis em estoque para verificação.

Quando o fornecedor entregar os insumos solicitados, o administrador do estoque poderá confirmar a lista de compras, e ela será automaticamente adicionada ao estoque. Quando os insumos forem separados para uma aula específica, o administrador marcará a lista de aula como "separada" e o sistema subtrairá automaticamente as quantidades solicitadas do estoque.
