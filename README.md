# Computação com Estados - Mutável vs Imutável

Este projeto demonstra os conceitos fundamentais de **computação com estados** em programação funcional, comparando abordagens **mutáveis** e **imutáveis** através de exemplos práticos em Scala.

## 📚 Conceitos Abordados

### Estado Mutável vs Estado Imutável
- **Estado Mutável**: Dados podem ser modificados após criação (efeitos colaterais)
- **Estado Imutável**: Dados não podem ser modificados, novas versões são criadas

### Padrões Funcionais para Gerenciamento de Estado
- Operações que retornam novos estados ao invés de modificar existentes
- Uso de `foldLeft` e `scanLeft` para computações sequenciais
- Preservação do histórico de estados em abordagens imutáveis

## 🗂️ Estrutura do Projeto

### Arquivos Fonte

#### `src/main/scala/Counters.scala`
Demonstra a diferença entre contadores mutáveis e imutáveis:

- **`mutableCounter`**: 
  - Object singleton com estado mutável compartilhado
  - Operações modificam diretamente o estado interno
  - Efeitos colaterais visíveis

- **`immutableCounter`**: 
  - Case class imutável 
  - Operações retornam novos contadores
  - Estados anteriores preservados

#### `src/main/scala/MutableAccount.scala`
Implementação de conta bancária com estado mutável:

- Classe com campo `balance` mutável
- Métodos `deposit` e `withdraw` modificam estado interno
- Demonstra problemas de efeitos colaterais

#### `src/main/scala/ImmutableAccount.scala`
Implementação de conta bancária com estado imutável usando 5 abordagens diferentes:

1. **`statefulComputationWithValBindings`**: 
   - Uso de val bindings para encadear operações
   - Cada operação retorna (saldo, nova_conta)

2. **`statefulComputationWithFoldLeft1`**: 
   - Uso de `foldLeft` com lista de operações
   - Foco no resultado final

3. **`statefulComputationWithFoldLeft2`**: 
   - Versão mais concisa usando syntax sugar (`_`)
   - Demonstra elegância funcional

4. **`statefulComputationWithFoldLeftAndHistory`**: 
   - `foldLeft` que mantém histórico de saldos
   - Acumulador como tupla (conta, histórico)

5. **`statefulComputationWithScanLeft`**: 
   - Uso de `scanLeft` para todos os estados intermediários
   - Preserva todos os estados da computação

## 🎯 Objetivos de Aprendizagem

Após estudar este projeto, você deve compreender:

1. **Diferenças fundamentais** entre estado mutável e imutável
2. **Vantagens da imutabilidade** na programação funcional
3. **Padrões funcionais** para computação com estados
4. **Uso prático** de `foldLeft` e `scanLeft` 
5. **Como preservar histórico** em computações funcionais

## 🚀 Como Executar

### Executar todos os exemplos:
```bash
sbt run
```

### Executar exemplos específicos:
```bash
# Contador mutável
sbt "runMain mutableCounter.testCounter"

# Contador imutável  
sbt "runMain immutableCounter.testCounter"

# Conta mutável
sbt "runMain mutableAccount.testAccount"

# Contas imutáveis (diferentes abordagens)
sbt "runMain immutableAccount.statefulComputationWithValBindings"
sbt "runMain immutableAccount.statefulComputationWithFoldLeft1"
sbt "runMain immutableAccount.statefulComputationWithFoldLeft2"
sbt "runMain immutableAccount.statefulComputationWithFoldLeftAndHistory"
sbt "runMain immutableAccount.statefulComputationWithScanLeft"
```

## 🔍 Experimentos Sugeridos

1. **Compare as saídas** dos exemplos mutáveis vs imutáveis
2. **Modifique as operações** bancárias e observe o comportamento
3. **Implemente novas operações** (transferência, juros, etc.)
4. **Analise o histórico** gerado pelas abordagens imutáveis
5. **Teste a concorrência** com múltiplas threads (mutável vs imutável)

## 💡 Pontos de Reflexão

- Por que a imutabilidade é preferível em programação funcional?
- Como `foldLeft` e `scanLeft` diferem em suas aplicações?
- Quais são os trade-offs entre performance e segurança?
- Como aplicar estes conceitos em projetos reais?
---

## 🛠️ Ambiente de Desenvolvimento

Este projeto utiliza **DevContainer** para garantir um ambiente consistente de desenvolvimento.

### Ferramentas Incluídas
- **Scala**: Linguagem principal
- **Metals**: Language server para VS Code com recursos avançados
- **SBT**: Ferramenta de build para projetos Scala

### Como Utilizar

#### 1. GitHub Codespaces (Recomendado)
- Clique em **"Code"** → **"Open with Codespaces"**
- Ambiente completo no navegador, sem instalação local
- Todas as ferramentas pré-configuradas

#### 2. VS Code + Docker (Local)
1. Instale [Docker](https://www.docker.com/) e [VS Code](https://code.visualstudio.com/)
2. Instale a extensão [Dev Containers](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers)
3. Abra o projeto no VS Code
4. Selecione "Reabrir no Container"

### Comandos Básicos
```bash
sbt compile    # Compila o projeto
sbt run        # Executa todos os exemplos
sbt console    # Abre o REPL Scala
```
