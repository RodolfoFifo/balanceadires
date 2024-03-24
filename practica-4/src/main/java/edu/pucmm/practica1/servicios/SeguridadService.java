package edu.pucmm.practica1.servicios;

import edu.pucmm.practica1.entidades.Rol;
import edu.pucmm.practica1.entidades.Usuario;
import edu.pucmm.practica1.repositorio.RolRepository;
import edu.pucmm.practica1.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SeguridadService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    public void crearUsuarioAdmin(){
        Usuario admin = new Usuario();
        Rol rolAdmin = new Rol("ROLE_USER");
        rolRepository.save(rolAdmin);
        rolAdmin = new Rol("ROLE_ADMIN");
        rolRepository.save(rolAdmin);
        Rol erol = rolAdmin;
        admin.setUsername("admin");
        admin.setPassword(bCryptPasswordEncoder.encode("1234"));
        admin.setNombre("Administrador");
        admin.setActivo(true);
        admin.setRoles(new HashSet<>(Arrays.asList(erol)));
        usuarioRepository.save(admin);
    }

    public void createUsuario(String nombre,String usuario,String contrasena,String rol){
        Rol erol = rolRepository.getReferenceById(rol);
        Usuario user = usuarioRepository.findByUsername(usuario);

        if(user == null) {
            user = new Usuario(usuario,bCryptPasswordEncoder.encode(contrasena), true, nombre, new HashSet<>(Arrays.asList(erol)));
            usuarioRepository.save(user);
        }
    }

    public List<Usuario> loadUsers(String user){
        return usuarioRepository.findAllUsersByUsername(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findByUsername(username);

        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (Rol role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isActivo(), true, true, true, grantedAuthorities);
    }

    public void updateRoleInUser(String rol,String user){
        Set<Rol> roles = new HashSet<Rol>();
        Rol r = rolRepository.getReferenceById(rol);
        roles.add(r);
        Usuario u = usuarioRepository.findByUsername(user);
        u.setRoles(roles);
        usuarioRepository.save(u);
    }

    public Usuario getUserById(String username){
        return usuarioRepository.findByUsername(username);
    }
}