# 🧱 Arena Battle Tanks (PvE)

Um sistema de simulação de batalhas entre **tanques autônomos (IA)** e **criados por jogadores humanos**.  
O projeto foi desenvolvido como desafio acadêmico para aplicar **Programação Orientada a Objetos (POO)** em Java, incluindo **herança, polimorfismo, listas e integração externa (CSV e testes unitários).**

---

## 🚀 Objetivo do Projeto

O sistema tem como finalidade **gerenciar arenas de batalha** que operam partidas com tanques de diferentes classes e modos de jogo, permitindo:

- Cadastro e controle de até **12 tanques simultâneos**  
- Agendamento de **partidas (Treino, 1v1 e Equipes 3v3, 5v5)**  
- Aplicação de **regras de dano polimórficas** por tipo de arma e terreno  
- **Relatórios** de desempenho, pontuação e ranking  

---

## ⚙️ Tecnologias Utilizadas

- **Java 17+**
- **Maven** (gerenciamento de dependências)
- **JUnit 5** (testes unitários)
- **OpenCSV** (importação/exportação de arquivos CSV)
- **Collections API (ArrayList, HashMap, etc.)**


---

## 🧠 Principais Conceitos de POO Aplicados

| Conceito | Implementação |

| **Herança** | `Tanque` é classe base de `Leve`, `Médio` e `Pesado`. |
| **Polimorfismo** | Método `arma.calcularDano(alvo)` é sobrescrito por `Canhão`, `Metralhadora` e `Míssil`. |

| **Encapsulamento** | Atributos privados com getters e setters. |
| **Listas e Arrays** | Utilização de `ArrayList` para tanques e partidas. |

| **Sobrecarga/Sobrescrita** | Métodos sobrecarregados para cálculo de recarga e dano. |

---

## 🕹️ Modos de Jogo

- **Treino:** arena aleatória, IA e tanques humanos.  
- **1v1:** duelo entre dois tanques escolhidos.  
- **Equipes (3v3 / 5v5):** batalhas em equipe com escolha de classes (leve, médio, pesado).  

---

## ⚔️ Classes e Armas

### 🛞 Classes de Tanque

| Tipo | Características |

| **Leve** | Alta velocidade, baixa blindagem, recarga rápida. |

| **Médio** | Atributos equilibrados e versáteis. |

| **Pesado** | Alta blindagem e dano, recarga lenta. |

### 🔫 Módulos de Armas

| Arma | Tipo de Dano | Características |

| **Canhão** | Perfuração | Dano elevado, recarga lenta. |

| **Metralhadora** | Fragmentação | Alta cadência, dano reduzido. |

| **Míssil** | Explosiva | Dano em área, vulnerável a falhas. |

---

## 📊 Regras de Cálculo de Dano

O dano final é calculado considerando:

- Tipo da arma + classe do atacante  
- Blindagem do alvo (setor frontal/lateral/traseiro)  
- Tipo de munição (perfurante, explosiva, fragmentação)  
- Condição da arena (deserto, urbano, campo aberto)  
- Modificadores de terreno e distância  

---

## 📅 Agendamento de Partidas

- Impede sobreposição de **tanques ou arenas** no mesmo horário  
- Permite registrar **data/hora**, **arena**, **modo** e **lista de tanques**  
- Exporta/Importa histórico em **CSV**  

---

## 🧾 Relatórios e Estatísticas

- Ranking global e por modo  
- Taxa de vitória (por classe e tipo de piloto)  
- Mapa de calor de horários  
- Top armas e munições por dano total  
- Relatórios detalhados de desempenho por tanque  

---

## 🧪 Testes Unitários (JUnit)

Exemplo de teste:

```java
@Test
public void testCalculoDanoCanhaoPesado() {
    Tanque pesado = new Pesado("Titan");
    Arma canhao = new Canhao();
    double dano = canhao.calcularDano(pesado);
    assertTrue(dano > 0);
}
```

---

## 💾 Importação e Exportação CSV

O sistema permite salvar e carregar:

- Tanques cadastrados  
- Histórico de partidas  
- Rankings e relatórios  

---

## ▶️ Como Executar

### 1. Clone o repositório:
```bash
git clone https://github.com/FelipeAlho/ARENA-BATTLE-TANKS.git
```

### 2. Compile o projeto:
```bash
mvn clean install
```

### 3. Execute o programa:
```bash
mvn exec:java -Dexec.mainClass="view.App"
```

### 4. (Opcional) Rode os testes:
```bash
mvn test
```

---

## 👤 Autores

**Artur Lobato, Arthur Angelim, Felipe Alho e Gabriel Augusto**  
💻 Projeto acadêmico desenvolvido para a disciplina **Construção de Componentes de Software II (CC2MA)**  
📅 Entrega: 06/10/2025  

