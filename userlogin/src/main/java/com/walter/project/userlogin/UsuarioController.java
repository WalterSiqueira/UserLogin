package com.walter.project.userlogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/user")
public class UsuarioController {
    public final UsuarioRepository userRepository;

    @Autowired
    private UsuarioService userService;

    public UsuarioController(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public List<Usuario> listarUsuarios() {
        return userRepository.findAll();
    }

    @PostMapping("/signin")
    public Usuario signinUser(@RequestBody Usuario user) {
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String senha) {
        boolean validado = userService.validarUsuario(email, senha);
        if (validado) {
            return ResponseEntity.ok("Login bem-sucedido");
        } else {
            return ResponseEntity.status(401).body("Credenciais Inválidas");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario userAtualizado) {
        Optional<Usuario> userExistente = userRepository.findById(id); 

        if (userExistente.isPresent()) {
            Usuario user = userExistente.get();
            user.setEmail(userAtualizado.getEmail());
            user.setNome(userAtualizado.getNome());
            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> deletarUser(@PathVariable Long id) {
        Optional<Usuario> userExistente = userRepository.findById(id);

        if(userExistente.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
