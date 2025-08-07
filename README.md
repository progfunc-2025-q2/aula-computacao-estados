# Ambiente de desenvolvimento Scala com DevContainer

Este repositório contém um ambiente de desenvolvimento pré-configurado utilizando **DevContainer** para a disciplina de Programação Funcional em Scala.

## O que é um DevContainer?
Um DevContainer é um ambiente de desenvolvimento isolado, baseado em containers, que garante que todos os alunos tenham as mesmas ferramentas e configurações, facilitando o desenvolvimento e evitando problemas de incompatibilidade.

## Ferramentas disponíveis
- **Scala**: Linguagem principal do curso.
- **Metals**: Extensão do VS Code que provê um language server para suporte avançado a Scala (autocompletar, lint, refatoração, etc).
- **SBT**: Ferramenta de build para projetos Scala.

## Como utilizar

### 1. Usando o GitHub Codespaces (recomendado)
O **GitHub Codespaces** permite que você abra este projeto em um ambiente de desenvolvimento completo, direto no navegador, sem precisar instalar nada na sua máquina. Basta clicar no botão **"Code"** no repositório do GitHub e selecionar **"Open with Codespaces"** (ou "Abrir com Codespaces").

- O Codespaces já configura automaticamente o DevContainer.
- Todas as ferramentas estarão prontas para uso.
- Basta abrir o terminal integrado e usar os comandos normalmente (`sbt compile`, `sbt run`, etc).
- **Esta é a forma preferencial de uso, pois elimina problemas de configuração local.**

### 2. Usando Docker e VS Code localmente
Caso prefira rodar localmente:

1. **Pré-requisitos**:
   - Tenha o [Docker](https://www.docker.com/) instalado em sua máquina.
   - Instale o [Visual Studio Code](https://code.visualstudio.com/) e a extensão [Dev Containers](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers).

2. **Abrindo o projeto no DevContainer**:
   - Abra o VS Code na pasta deste projeto.
   - Clique no canto inferior esquerdo (><) e selecione "Reabrir no Container" (ou use o comando `Dev Containers: Reopen in Container`).
   - Aguarde o container ser criado e inicializado.

3. **Utilizando as ferramentas**:
   - O terminal já estará configurado com Scala e SBT.
   - Use o comando `sbt` para compilar, rodar e testar seus programas.
   - A extensão Metals estará ativa, oferecendo recursos como autocompletar, navegação de código e sugestões.

4. **Executando seu código**:
   - No terminal, utilize:
     - `sbt compile` para compilar o projeto.
     - `sbt run` para executar o programa principal.
     - `sbt test` para rodar os testes (se houver).

## Usando o Scala REPL

O **Scala REPL** (Read-Eval-Print Loop) é um interpretador interativo que permite testar pequenos trechos de código Scala rapidamente, sem precisar criar arquivos ou compilar o projeto inteiro.

Para abrir o REPL dentro do DevContainer (no Codespaces ou localmente):

1. Abra o terminal integrado do VS Code.
2. Digite o comando:
   
   ```bash
   scala
   ```
   ou, se preferir usar o SBT console (com dependências do projeto):
   ```bash
   sbt console
   ```
3. Você poderá digitar comandos Scala e ver o resultado imediatamente.

O REPL é útil para experimentar funções, testar expressões e aprender Scala de forma interativa.

## Dicas
- Sempre que atualizar dependências ou arquivos de configuração, reinicie o container para garantir que tudo funcione corretamente.
- Consulte a documentação do [Metals](https://scalameta.org/metals/) e do [SBT](https://www.scala-sbt.org/) para tirar dúvidas sobre as ferramentas.

---

Qualquer dúvida, entre em contato com o professor ou com o grupo de colegas da disciplina.
