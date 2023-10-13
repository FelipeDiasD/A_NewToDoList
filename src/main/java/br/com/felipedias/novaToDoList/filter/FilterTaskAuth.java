package br.com.felipedias.novaToDoList.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.felipedias.novaToDoList.repository.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;


@Component
public class FilterTaskAuth extends OncePerRequestFilter {


    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if(servletPath.equals("/tasks")){

            var authorization = request.getHeader("Authorization");

            var authEncoded = authorization.substring("Basic".length()).trim();

            byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

            var authString = new String(authDecoded);

            String [] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            //Validando user
            var foundUser = this.userRepository.findByUsername(username);

            if(foundUser == null){
                response.sendError(401);
            }
            else{
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), foundUser.getPassword());
                if(passwordVerify.verified){
                    request.setAttribute("userId",foundUser.getId());
                    filterChain.doFilter(request,response);
                    }
                else {
                    response.sendError(401);
                     }
                }

            System.out.println(authString);


            }

        else {
            filterChain.doFilter(request,response);
        }

        }


}


