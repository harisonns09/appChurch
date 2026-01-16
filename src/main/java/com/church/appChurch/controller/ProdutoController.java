package com.church.appChurch.controller;

import com.church.appChurch.model.Produto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.IntStream;

@RestController()
@RequestMapping("/api")
public class ProdutoController {

    private ArrayList<Produto> database;

    public ProdutoController() {
        database = new ArrayList<>() {{
            add(new Produto(1, "Computador", 1500.0));
            add(new Produto(2, "Mouse", 50.0));
            add(new Produto(3, "Teclado", 50.0));
            add(new Produto(4, "Monitor",  500.0));

        }};
    }

    @GetMapping("/produtos")
    public ArrayList<Produto> recuperarTodos() {
        return database;
    }

    @GetMapping("/produtos/{id}")
    public Produto recuperarPorId(@PathVariable int id) {
        return database.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    @PostMapping("/produtos")
    public Produto adicionarProduto(@RequestBody Produto novo) {
        database.add(novo);
        return novo;
    }

    @PutMapping("/produtos/{id}")
    public Produto atualizarProduto(@PathVariable int id, @RequestBody Produto novo) {
        int posicao = IntStream.range(0, database.size())
                .filter(i -> database.get(i).getId() == id)
                .findFirst().orElse(-1);

        if (posicao >= 0) {
            database.set(posicao, novo);
            return novo;
        }

        return null;
    }
}
