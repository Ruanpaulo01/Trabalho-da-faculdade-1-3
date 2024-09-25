package principal;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Integer.parseInt;
import java.util.List;
import java.util.Scanner;

import model.entidades.PessoaFisica;
import model.entidades.PessoaJuridica;
import model.gerenciadores.PessoaFisicaRepo;
import model.gerenciadores.PessoaJuridicaRepo;

public class MainMenu {

    private static Scanner scanner = new Scanner(System.in);
    private static PessoaFisicaRepo repoFisica = new PessoaFisicaRepo();
    private static PessoaJuridicaRepo repoJuridica = new PessoaJuridicaRepo();

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer do scanner
            processarOpcao(opcao);
        } while (opcao != 0);
    }

    private static void exibirMenu() {
        System.out.println("Escolha uma opcao:");
        System.out.println("=====================");
        System.out.println("1 - Incluir Pessoa");
        System.out.println("2 - Alterar Pessoa");
        System.out.println("3 - Excluir Pessoa");
        System.out.println("4 - Buscar pelo ID");
        System.out.println("5 - Exibir Todos");
        System.out.println("6 - Persistir Dados");
        System.out.println("7 - Recuperar Dados");
        System.out.println("0 - Finalizar Programa");
        System.out.println("=====================");
        System.out.print("Digite uma opcao: ");
    }

    private static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 ->
                incluir();
            case 2 ->
                alterar();
            case 3 ->
                excluir();
            case 4 ->
                exibirPorId();
            case 5 ->
                exibirTodos();
            case 6 ->
                salvarDados();
            case 7 ->
                recuperarDados();
            case 0 ->
                System.out.println("Finalizando...");
            default ->
                System.out.println("\n ## Opção invalida! ##");
        }
    }

    private static void incluir() {
        System.out.println("Incluir Pessoa (1 - Fisica, 2 - Juridica): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        if (tipo == 1) {
            PessoaFisica pf = new PessoaFisica();

            System.out.println("Digite um ID: ");
            pf.setId(parseInt(scanner.nextLine()));

            System.out.println("Digite o nome: ");
            pf.setNome(scanner.nextLine());

            System.out.println("Digite o CPF: ");
            pf.setCpf(scanner.nextLine());

            System.out.println("Digite a idade: ");
            int idade = scanner.nextInt();
            scanner.nextLine();

            pf.setIdade(idade);

            repoFisica.inserir(pf);
            System.out.println("\n## Pessoa Fisica adicionada com sucesso! ##");
        } else if (tipo == 2) {
            PessoaJuridica pj = new PessoaJuridica();

            System.out.println("Digite um ID: ");
            pj.setId(parseInt(scanner.nextLine()));

            System.out.println("Digite o nome:");
            pj.setNome(scanner.nextLine());

            System.out.println("Digite o CNPJ:");
            pj.setCnpj(scanner.nextLine());

            repoJuridica.inserir(pj);
            System.out.println("\n## Pessoa Juridica adicionada com sucesso! ##");
        } else {
            System.out.println("\n## Tipo invalido. ##");
        }
    }

    private static void alterar() {
        System.out.println("Alterar Pessoa (1 - Fisica, 2 - Juridica): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite o ID da pessoa:");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (tipo == 1) {
            PessoaFisica pf = repoFisica.obter(id);
            if (pf != null) {
                System.out.println("Dados atuais: ");
                pf.exibir();

                System.out.println("Digite o novo nome (deixe em branco para nao alterar):");
                String nome = scanner.nextLine();
                if (!nome.isEmpty()) {
                    pf.setNome(nome);
                }

                System.out.println("Digite o novo CPF (deixe em branco para nao alterar):");
                String cpf = scanner.nextLine();
                if (!cpf.isEmpty()) {
                    pf.setCpf(cpf);
                }

                System.out.println("Digite a nova idade (insira 0 para nao alterar):");
                int idade = scanner.nextInt();
                scanner.nextLine();
                if (idade != 0) {
                    pf.setIdade(idade);
                }

                repoFisica.alterar(pf);
                System.out.println("\n## Pessoa Fisica atualizada com sucesso!");
            } else {
                System.out.println("\n## Pessoa Fisica não encontrada.");
            }
        } else if (tipo == 2) {
            PessoaJuridica pj = repoJuridica.obter(id);
            if (pj != null) {
                System.out.println("Dados atuais: ");
                pj.exibir();

                System.out.println("Digite o novo nome (deixe em branco para nao alterar):");
                String nome = scanner.nextLine();
                if (!nome.isEmpty()) {
                    pj.setNome(nome);
                }

                System.out.println("Digite o novo CNPJ (deixe em branco para nao alterar):");
                String cnpj = scanner.nextLine();
                if (!cnpj.isEmpty()) {
                    pj.setCnpj(cnpj);
                }

                repoJuridica.alterar(pj);
                System.out.println("\n## Pessoa Juridica atualizada com sucesso! ##");
            } else {
                System.out.println("\n## Pessoa Juridica não encontrada. ##");
            }
        } else {
            System.out.println("\n## Tipo invalido. ##");
        }
    }

    private static void excluir() {
        System.out.println("Excluir Pessoa (1 - Fisica, 2 - Juridica): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite o ID da pessoa a ser excluida:");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (tipo == 1) {
            PessoaFisica pf = repoFisica.obter(id);
            if (pf != null) {
                repoFisica.excluir(id);
                System.out.println("\n## Pessoa Fisica removida com sucesso! ##");
            } else {
                System.out.println("\n## Pessoa Fisica não encontrada. ##");
            }
        } else if (tipo == 2) {
            PessoaJuridica pj = repoJuridica.obter(id);
            if (pj != null) {
                repoJuridica.excluir(id);
                System.out.println("\n## Pessoa Juridica removida com sucesso! ##");
            } else {
                System.out.println("\n## Pessoa Juridica nao encontrada. ##");
            }
        } else {
            System.out.println("\n## Tipo invalido. ##");
        }
    }

    private static void exibirPorId() {
        System.out.println("Exibir dados de Pessoa (1 - Fisica, 2 - Juridica): ");
        int tipo = scanner.nextInt();
        scanner.nextLine(); 
        System.out.println("Digite o ID da pessoa:");
        int id = scanner.nextInt();
        scanner.nextLine(); 

        if (tipo == 1) {
            PessoaFisica pf = repoFisica.obter(id);
            if (pf != null) {
                System.out.println("Dados da Pessoa Fisica:");
                pf.exibir();
            } else {
                System.out.println("\n## Pessoa Fisica nao encontrada. ##");
            }
        } else if (tipo == 2) {
            PessoaJuridica pj = repoJuridica.obter(id);
            if (pj != null) {
                System.out.println("Dados da Pessoa Juridica:");
                pj.exibir();
            } else {
                System.out.println("\n## Pessoa Jurídica nao encontrada. ##");
            }
        } else {
            System.out.println("\n## Tipo invalido. ##");
        }
    }

    private static void exibirTodos() {
        System.out.println("Exibir todos (1 - Fisica, 2 - Juridica): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        if (tipo == 1) {
            System.out.println("Lista de Todas as Pessoas Fisicas:");
            for (PessoaFisica pf : repoFisica.obterTodos()) {
                pf.exibir();
                System.out.println("---------------------");
            }
        } else if (tipo == 2) {
            System.out.println("Lista de Todas as Pessoas Juridicas:");
            for (PessoaJuridica pj : repoJuridica.obterTodos()) {
                pj.exibir();
                System.out.println("---------------------");
            }
        } else {
            System.out.println("\n## Tipo invalido. ##");
        }
    }

    private static void salvarDados() {
        System.out.println("Digite o prefixo para salvar os arquivos:");
        String prefixo = scanner.nextLine();

        
        try (ObjectOutputStream oosFisica = new ObjectOutputStream(new FileOutputStream(prefixo + ".fisica.bin"))) {
            oosFisica.writeObject(repoFisica.obterTodos());
            System.out.println("\n## Dados de Pessoas Fisicas salvos com sucesso.");
        } catch (IOException e) {
            System.err.println("\n## Erro ao salvar dados de Pessoas Fisicas: " + e.getMessage());
        }

        
        try (ObjectOutputStream oosJuridica = new ObjectOutputStream(new FileOutputStream(prefixo + ".juridica.bin"))) {
            oosJuridica.writeObject(repoJuridica.obterTodos());
            System.out.println("\n## Dados de Pessoas Juridicas salvos com sucesso. ##");
        } catch (IOException e) {
            System.err.println("\n ##Erro ao salvar dados de Pessoas Juridicas: " + e.getMessage());
        }
    }

    private static void recuperarDados() {
        System.out.println("Digite o prefixo dos arquivos para recuperacao:");
        String prefixo = scanner.nextLine();

       
        try (ObjectInputStream oisFisica = new ObjectInputStream(new FileInputStream(prefixo + ".fisica.bin"))) {
            List<PessoaFisica> listaFisica = (List<PessoaFisica>) oisFisica.readObject();
            repoFisica.setLista(listaFisica);
            System.out.println("\n##Dados de Pessoas Fisicas recuperados com sucesso. ##");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("\n## Erro ao recuperar dados de Pessoas Fisicas: " + e.getMessage());
        }

        
        try (ObjectInputStream oisJuridica = new ObjectInputStream(new FileInputStream(prefixo + ".juridica.bin"))) {
            List<PessoaJuridica> listaJuridica = (List<PessoaJuridica>) oisJuridica.readObject();
            repoJuridica.setLista(listaJuridica);
            System.out.println("\n ## Dados de Pessoas Juridicas recuperados com sucesso. ##");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("\n## Erro ao recuperar dados de Pessoas Juridicas: " + e.getMessage());
        }
    }

}
