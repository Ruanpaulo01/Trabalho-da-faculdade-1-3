package principal;

import java.io.IOException;
import model.entidades.PessoaFisica;
import model.entidades.PessoaJuridica;
import model.gerenciadores.PessoaFisicaRepo;
import model.gerenciadores.PessoaJuridicaRepo;

public class Main {

    public static void main(String[] args) {
        PessoaFisicaRepo repo1 = new PessoaFisicaRepo();

        PessoaFisica pf1 = new PessoaFisica(1, "Julio Marques", "123.456.789-00", 36);
        PessoaFisica pf2 = new PessoaFisica(2, "Ingrid Albuquerque", "987.654.321-00", 35);

        repo1.inserir(pf1);
        repo1.inserir(pf2);

        String nomeArquivo = "pessoasFisicas.dat";
        try {
            repo1.persistir(nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro: " + e);
        }

        PessoaFisicaRepo repo2 = new PessoaFisicaRepo();

        try {
            repo2.recuperar(nomeArquivo);
            System.out.println("## Dados de Pessoas Fisicas Armazenados");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro: " + e);
        }

        System.out.println("### Pessoas Cadastradas:");
        for (PessoaFisica pf : repo2.obterTodos()) {
            pf.exibir();
        }

        PessoaJuridicaRepo repo3 = new PessoaJuridicaRepo();

        PessoaJuridica pj1 = new PessoaJuridica(1, "XPTO Sales", "12.345.678/0001-99");
        PessoaJuridica pj2 = new PessoaJuridica(2, "XPTO Solutions", "98.765.432/0001-11");

        repo3.inserir(pj1);
        repo3.inserir(pj2);

        String nomeArquivoPJ = "pessoasJuridicas.dat";
        try {
            repo3.persistir(nomeArquivoPJ);
            System.out.println("\n## Dados de Pessoas Juridicas Armazenados");
        } catch (IOException e) {
            System.err.println("Erro Encontrado: " + e);
        }

        PessoaJuridicaRepo repo4 = new PessoaJuridicaRepo();

        String nomeArquivoPJ2 = "pessoasJuridicas.dat"; 
        try {
            repo4.recuperar(nomeArquivoPJ2);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro Encontrado: " + e);
        }

        System.out.println("### Pessoas Juridicas Recuperadas:");
        for (PessoaJuridica pj : repo4.obterTodos()) {
            pj.exibir();
        }

    }
}
