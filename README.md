# Jogo de Xadrez em Java

Este projeto foi desenvolvido pelo aluno Henrique Alves de Fernando como parte da disciplina MC322 - Programação Orientada a Objetos, ministrada na Universidade Estadual de Campinas (Unicamp).

## Autor

- Henrique Alves de Fernando
- RA: 236538

## Descrição do Projeto

O projeto consiste em um jogo de xadrez implementado em Java, com o objetivo de aplicar os conceitos de programação orientada a objetos. Ele oferece uma interface gráfica utilizando Java Swing e Java AWT para proporcionar uma experiência interativa aos jogadores.

## Funcionalidades

- **Jogo entre duas pessoas ou entre pessoa e computador:** O jogo permite que dois jogadores joguem localmente ou um jogador jogue contra o computador, sendo que ele pode também escolher jogar com as peças brancas, pretas ou aleatoriamente
- **Movimento de peças via clique ou arrastando:** Os jogadores podem movimentar as peças clicando nelas e na casa de destino ou arrastando as peças até a casa de destino
- **Apresentação dos movimentos possíveis:** Ao clicar em uma peça, são exibidas as casas de movimento possíveis para essa peça na interface gráfica
- **Lances inválidos bloqueados:** As regras do xadrez são implementadas para garantir que apenas movimentos válidos sejam permitidos
- **Lances especiais:** Os lances especiais do jogo, como en passant e roque, são implementados usando uma interface chamada MovableSpc
- **Promoção do peão para qualquer peça:** Quando um peão chega na casa de promoção, é exibido um painel com as opções de peça de promoção, a qual deve ser selecionda
- **Escrita dos lances:** Os lances feitos pelos jogadores são registrados em um arquivo de texto
- **Verificação automática de fim e possibilidade de novo jogo:** Ao fim do jogo, é exibida uma mensagem informando como o jogo acabou e é dada ao usuário a opção de jogar novamente ou sair

## Como Jogar

1. Ao iniciar o jogo, o jogador deve escolher se deseja jogar contra o computador ou contra outro jogador humano
2. Se o jogador optar por jogar contra o computador, ele poderá escolher jogar com as peças brancas, pretas ou aleatoriamente
3. O jogador pode mover as peças clicando nelas e na casa de destino ou arrastando as peças até a casa de destino
4. Ao clicar em uma peça, as casas de movimento possíveis para essa peça serão destacadas na interface gráfica, seguindo o seguinte padrão:
	- Casa azul: lance comum
	- Casa vermelha: captura de peça adversária
	- Casa roxa: lance especial (roque ou en passant)
5. Ao chegar com um peão em sua casa de promoção, é exibida uma caixa de diálogo em que deve-se selecionar a peça para a qual ele será promovido
6. O jogo segue as regras do xadrez, não permitindo lances inválidos
7. Ao finalizar o jogo, uma mensagem será exibida perguntando se o jogador deseja jogar novamente ou sair do jogo
	- Se o jogador escolher jogar novamente, o programa será reiniciado.
	- Se o jogador escolher sair, o programa será finalizado.

## Requisitos do Projeto

- Java JDK (versão compatível com o Java Swing e Java AWT)
- IDE Eclipse (ou qualquer outra IDE Java)
- Bibliotecas Swing e AWT (incluídas no JDK)

## Estrutura do Projeto

Todas as classes do projeto podem ser vistas no diagrama UML Xadrez_UML. As principais classes usadas são as seguintes:
- **Classe Peça:** Classe abstrata que representa uma peça do jogo
- **Classe Jogador:** Classe abstrata que representa um jogador (humano ou máquina)
- **Interface MovableSpc:** Interface que define os lances especiais do jogo
- **Classe Tabuleiro:** Classe responsável por gerenciar o tabuleiro do jogo e também a interface gráfica
- **Classe Jogo:** Classe que controla o fluxo de lances do jogo
- **Classe Casa:** Classe que representa uma casa do tabuleiro
- **Classe Lance:** Classe que representa um lance feito por um jogador
- **Classes NenhumaPecaSelecionadaException e PecaOponenteSelecionadaException:** Classes que representam exceções pertinentes ao jogo que são lançadas quando necessárioo

## Vídeo explicativo:

https://drive.google.com/file/d/1eXV1Cz8QVLmr9v2u99eZ-0p574en-RqN/view