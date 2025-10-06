import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static List<Tanque> tanques = new ArrayList<>();
    private static List<Partida> partidas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int nextTanqueId = 1;
    private static Ranking ranking = new Ranking();
    
    public static void main(String[] args) {
        System.out.println("ARENA BATTLE TANKS - CENTRO DE TREINAMENTO");
        criarTanquesExemplo();
        
        int opcao;
        do {
            System.out.println("\nMENU PRINCIPAL");
            System.out.println("1. Cadastrar Tanque");
            System.out.println("2. Listar Tanques");
            System.out.println("3. Agendar Partida");
            System.out.println("4. Listar Partidas");
            System.out.println("5. Simular Batalha");
            System.out.println("6. Ver Estatisticas");
            System.out.println("7. Relatorios e Ranking");
            System.out.println("8. Manutencao e Estados");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1: cadastrarTanque(); break;
                case 2: listarTanques(); break;
                case 3: agendarPartida(); break;
                case 4: listarPartidas(); break;
                case 5: simularBatalha(); break;
                case 6: verEstatisticas(); break;
                case 7: mostrarRelatorios(); break;
                case 8: gerenciarManutencao(); break;
                case 0: System.out.println("Saindo..."); break;
                default: System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);
    }
    
    private static void criarTanquesExemplo() {
        tanques.add(new TanqueLeve("LT-001", "Lobatao", "Humano"));
        tanques.add(new TanqueMedio("MT-001", "Alho", "IA"));
        tanques.add(new TanquePesado("HT-001", "Angelim", "Humano"));
        tanques.add(new TanqueLeve("LT-002", "Gabriel", "IA"));
        tanques.add(new TanqueMedio("MT-002", "Isac", "Humano"));
        nextTanqueId = 6;
    }
    
    private static void cadastrarTanque() {
        System.out.println("\nCADASTRAR TANQUE");
        
        if (tanques.size() >= 12) {
            System.out.println("Limite de 12 tanques atingido!");
            return;
        }
        
        System.out.print("Codinome: ");
        String codinome = scanner.nextLine();
        
        System.out.println("Classe:");
        System.out.println("1. Leve");
        System.out.println("2. Medio"); 
        System.out.println("3. Pesado");
        System.out.print("Escolha: ");
        int classe = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Piloto (Humano/IA): ");
        String piloto = scanner.nextLine();
        
        String id = String.format("T-%03d", nextTanqueId++);
        Tanque novoTanque;
        
        switch (classe) {
            case 1: novoTanque = new TanqueLeve(id, codinome, piloto); break;
            case 2: novoTanque = new TanqueMedio(id, codinome, piloto); break;
            case 3: novoTanque = new TanquePesado(id, codinome, piloto); break;
            default: 
                System.out.println("Classe invalida!");
                return;
        }
        
        tanques.add(novoTanque);
        System.out.println("Tanque " + codinome + " cadastrado com sucesso!");
    }
    
    private static void listarTanques() {
        System.out.println("\nTANQUES CADASTRADOS (" + tanques.size() + "/12)");
        for (int i = 0; i < tanques.size(); i++) {
            System.out.println((i+1) + ". " + tanques.get(i).getInfoCompleta());
        }
    }
    
    private static void agendarPartida() {
        System.out.println("\nAGENDAR PARTIDA");
        
        System.out.print("Modo (Treino/1v1/3v3/5v5): ");
        String modo = scanner.nextLine();
        
        System.out.print("Arena (Deserto/Urbano/Campo Aberto): ");
        String arena = scanner.nextLine();
        
        System.out.print("Duracao (minutos): ");
        int duracao = scanner.nextInt();
        scanner.nextLine();
        
        if (verificarConflitoArena(arena)) {
            System.out.println("Arena " + arena + " ja esta em uso neste horario!");
            System.out.print("Deseja continuar mesmo assim? (s/n): ");
            String resposta = scanner.nextLine();
            if (!resposta.equalsIgnoreCase("s")) {
                return;
            }
        }
        
        Partida partida = new Partida(modo, arena, duracao);
        
        System.out.println("\nSelecionar tanques para a partida:");
        listarTanquesDisponiveis();
        System.out.println("Digite os numeros dos tanques (ex: 1 3 5), 0 para terminar:");
        
        while (true) {
            int num = scanner.nextInt();
            if (num == 0) break;
            if (num > 0 && num <= tanques.size()) {
                Tanque tanque = tanques.get(num-1);
                if (!tanque.isDisponivel()) {
                    System.out.println(tanque.getCodinome() + " nao esta disponivel!");
                    continue;
                }
                if (partida.verificarConflitoTanque(tanque)) {
                    System.out.println(tanque.getCodinome() + " ja esta em outra partida!");
                    continue;
                }
                partida.adicionarTanque(tanque);
                System.out.println(tanque.getCodinome() + " adicionado");
            }
        }
        
        partidas.add(partida);
        System.out.println("Partida agendada! ID: " + partida.getId());
    }
    
    private static boolean verificarConflitoArena(String arena) {
        for (Partida p : partidas) {
            if (p.getArena().equalsIgnoreCase(arena) && !p.isFinalizada()) {
                return true;
            }
        }
        return false;
    }
    
    private static void listarTanquesDisponiveis() {
        for (int i = 0; i < tanques.size(); i++) {
            Tanque t = tanques.get(i);
            String status = t.isDisponivel() ? "DISPONIVEL" : "INDISPONIVEL";
            System.out.println((i+1) + ". " + status + " " + t.getInfoCompleta());
        }
    }
    
    private static void listarPartidas() {
        System.out.println("\nPARTIDAS AGENDADAS");
        for (Partida p : partidas) {
            System.out.println(p);
            if (p.isFinalizada()) {
                System.out.println("   " + p.getVencedor());
            }
        }
    }
    
    private static void simularBatalha() {
        if (partidas.isEmpty()) {
            System.out.println("Nenhuma partida agendada!");
            return;
        }
        
        System.out.println("\nSIMULAR BATALHA");
        for (int i = 0; i < partidas.size(); i++) {
            System.out.println((i+1) + ". " + partidas.get(i).getInfoSimples());
        }
        
        System.out.print("Escolha a partida: ");
        int idx = scanner.nextInt() - 1;
        
        if (idx >= 0 && idx < partidas.size()) {
            Partida partida = partidas.get(idx);
            partida.simular();
            ranking.atualizarRanking(partida);
        } else {
            System.out.println("Partida invalida!");
        }
    }
    
    private static void verEstatisticas() {
        System.out.println("\nESTATISTICAS GERAIS");
        
        System.out.println("\nDistribuicao por Classe:");
        Map<String, Integer> porClasse = new HashMap<>();
        for (Tanque t : tanques) {
            porClasse.put(t.getClasse(), porClasse.getOrDefault(t.getClasse(), 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : porClasse.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " tanques");
        }
        
        System.out.println("\nDistribuicao por Piloto:");
        Map<String, Integer> porPiloto = new HashMap<>();
        for (Tanque t : tanques) {
            porPiloto.put(t.getPiloto(), porPiloto.getOrDefault(t.getPiloto(), 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : porPiloto.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " tanques");
        }
        
        System.out.println("\nEstatisticas de Combate:");
        for (Tanque t : tanques) {
            if (t.getAbates() > 0 || t.getDanoCausado() > 0) {
                System.out.printf("  %s: %d abates, %.1f dano causado\n", 
                                t.getCodinome(), t.getAbates(), t.getDanoCausado());
            }
        }
    }
    
    private static void mostrarRelatorios() {
        System.out.println("\nRELATORIOS E RANKING");
        
        System.out.println("\nRANKING GERAL:");
        ranking.mostrarRanking();
        
        System.out.println("\nTOP ARMAS POR DANO:");
        mostrarTopArmas();
        
        System.out.println("\nMAPA DE CALOR DE ARENAS:");
        mostrarMapaCalorArenas();
        
        System.out.println("\nTAXA DE VITORIA POR CLASSE:");
        mostrarTaxaVitoriaClasse();
    }
    
    private static void mostrarTopArmas() {
        Map<String, Double> danoPorArma = new HashMap<>();
        for (Tanque t : tanques) {
            for (String arma : t.getArmasUsadas()) {
                danoPorArma.put(arma, danoPorArma.getOrDefault(arma, 0.0) + t.getDanoPorArma(arma));
            }
        }
        
        danoPorArma.entrySet().stream()
            .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
            .limit(3)
            .forEach(entry -> System.out.printf("  %s: %.1f dano total\n", entry.getKey(), entry.getValue()));
    }
    
    private static void mostrarMapaCalorArenas() {
        Map<String, Integer> usoArenas = new HashMap<>();
        for (Partida p : partidas) {
            usoArenas.put(p.getArena(), usoArenas.getOrDefault(p.getArena(), 0) + 1);
        }
        
        usoArenas.entrySet().stream()
            .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
            .forEach(entry -> System.out.printf("  %s: %d partidas\n", entry.getKey(), entry.getValue()));
    }
    
    private static void mostrarTaxaVitoriaClasse() {
        System.out.println("  Leve: 45% (em desenvolvimento)");
        System.out.println("  Medio: 52% (em desenvolvimento)");
        System.out.println("  Pesado: 48% (em desenvolvimento)");
    }
    
    private static void gerenciarManutencao() {
        System.out.println("\nGERENCIAR MANUTENCAO");
        
        for (int i = 0; i < tanques.size(); i++) {
            Tanque t = tanques.get(i);
            System.out.println((i+1) + ". " + t.getInfoManutencao());
        }
        
        System.out.print("\nEscolha tanque para alterar status (0 para cancelar): ");
        int idx = scanner.nextInt() - 1;
        scanner.nextLine();
        
        if (idx >= 0 && idx < tanques.size()) {
            Tanque tanque = tanques.get(idx);
            System.out.println("Status atual: " + tanque.getStatus());
            System.out.println("Novo status (Ativo/Destruido/Manutencao): ");
            String novoStatus = scanner.nextLine();
            tanque.setStatus(novoStatus);
            System.out.println("Status atualizado!");
            
            if (novoStatus.equalsIgnoreCase("Destruido")) {
                tanque.setIntegridade(0);
            } else if (novoStatus.equalsIgnoreCase("Ativo")) {
                tanque.setIntegridade(100);
            }
        }
    }
}