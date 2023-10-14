# Uma Nova To Do List


## Conteúdo

- [Visão geral](#visão-geral)
    - [A applicação](#a-aplicação)
    - [Links](#links)
- [Meu desenvolvimento](#meu-desenvolvimento)
    - [Ferramentas utilizadas](#ferramentas-utilizadas)
    - [O que aprendi](#o-que-aprendi)
    - [Próximos passos](#próximos-passos)
    - [Recursos úteis](#recursos-úteis)
- [Autor](#autor)


## Visão geral
Essa aplicação foi construída com o simples propósito de revisitar
alguns conceitos básicos acerca do densenvolvimento com Springboot e acompanhar o evento
de lançamento da trilha de Java na Rocketseat.


### A aplicação
Uma aplicação web service desenvolvida utilizando Java e o framework Springboot.
Para acessar e consumir a aplicação, recomendo utilizar o Postman ou apps similares.

#### Endpoints
###### Criar usuário -> POST - https://todolist-rocketweek.onrender.com/user
```json
Dados necessários:
{
"name": "Felipe Dias",
"username":"felipe",
"password":"12345"
}
```

###### Criar tarefa -> POST - https://todolist-rocketweek.onrender.com/tasks/
- Para criar uma tarefa é necessário utilizar a autenticação de um usuário existente
- Username e senha cadastrados
```json
Dados necessários:
{

  "description":"Levar o cachorro para passear no parque do centro durante uma hora e meia",
  "title":"passear",
  "priority": "Importante",
  "startAt":"2023-10-30T08:30:00",
  "endAt":"2023-10-30T11:30:00"

}
```

###### Pesquisar tarefas -> GET - https://todolist-rocketweek.onrender.com/tasks/
- Para recuperar uma tarefa é necessário utilizar a autenticação de um usuário existente
- Username e senha cadastrados

###### Atualizar tarefas -> PUT - https://todolist-rocketweek.onrender.com/tasks/UUID-da-tarefa
- Para atualizar uma tarefa é necessário inserir o ID da tarefa desejada e também utilizar a autenticação de um usuário existente
- Username e senha cadastrados
```json
Dados necessários:
{

  "title": "Lavagem",
  "description": "Lavar a roupa",
  "startAt": "2023-10-30T08:30:00",
  "endAt": "2023-10-30T11:30:00",
  "priority": "Importante"

}
```

### Links


- URL do deploy: [ToDoList](https://todolist-rocketweek.onrender.com)

## Meu desenvolvimento

Durante o desenvolvimento acompanhei as aulas do dia 09/10/23 ao dia 13/10/23
e apesar de visitar conceitos que já havia aprendi, me surpreendi com algumas coisas
novas que detalharei nas próximas secções.

### Ferramentas utilizadas

- Java 17
- Spring boot 3 framework
- Lombok
- Bycript



### O que aprendi

Um dos aprendizados que tive foi a utilização da notação @CreationTimeStamp
do Springboot, que permite a criação automática de uma data numa determinada entidade.
Veja o exemplo abaixo

```java
@Entity(name = "tb_user")
@Data
public class UserModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String username;

    private String name;
    private String password;
    
    //Será registrado o momento exato que o objeto for criado no banco de dados
    @CreationTimestamp
    private LocalDateTime createdAt;
}
```

Outro ponto de aprendizado a se destacar foi o uso da biblioteca Bycript para criptografia
da senha do usuário no banco de dados. Podemos ver um exemplo no método create relacionado
ao usuário.

```java
@PostMapping
public ResponseEntity create(@RequestBody UserModel user){


        if(userRepository.findByUsername(user.getUsername()) != null){
        return ResponseEntity.badRequest().body("Usuário com este username já existe");
            }

        var passwordHashred = BCrypt.withDefaults()
        .hashToString(12, user.getPassword().toCharArray());

        user.setPassword(passwordHashred);

        UserModel returnedObj = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnedObj);
            }
}
```

Também revisitei os filtros de requisição, que nesse caso foram utilizados para realizar
a autenticação do usuário no momento de criação de alguma tarefa.

```java
 @Override
protected void doFilterInternal
        (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.startsWith("/tasks/")){

        var authorization = request.getHeader("Authorization");

        var authEncoded = authorization.substring("Basic".length()).trim();

        byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

        var authString = new String(authDecoded);

        String [] credentials = authString.split(":");
        String username = credentials[0];
        String password = credentials[1];

        //Validando user
        var user = this.userRepository.findByUsername(username);

        if(user == null)    {
            response.sendError(401);
            }
        else    {
            var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

            if(passwordVerify.verified){
                request.setAttribute("userId",user.getId());
                filterChain.doFilter(request,response);
             }
            else {
                response.sendError(401);
            }
        }

            }

        else {
            filterChain.doFilter(request,response);
        }

}

```
Por fim também aprendi a realizar o deploy em uma plataforma nova, a [Render](render.com), 
utilizando apenas um dockerfile.


```java
    FROM ubuntu:latest as build
    RUN apt-get update
    RUN apt-get install openjdk-17-jdk -y

    COPY . .

    RUN apt-get install maven -y 
    RUN mvn clean install

    FROM openjdk:17-jdk-slim

    EXPOSE 8080

    COPY --from=build /target/novaToDoList-1.0.0.jar app.jar

    ENTRYPOINT ["java", "-jar", "app.jar"]
```


### Próximos passos

Acredito que para os próximos passos, temos a criação de testes
unitários e também um frontend para consumir as requisições. Podemos adicionar
mais funcionalidades e validações à aplicação, porém acredito que a mesma
já cumpre ao que se propõe. Feedbacks são bem-vindos!

### Recursos úteis
-  [Spring initializer](https://start.spring.io)
- [Documentação springboot](https://spring.io/guides/gs/spring-boot/)
## Autor

- Website(GitHub) - [Felipe Dias](https://github.com/FelipeDiasD)
- LinkedIn - [Felipe Dias](https://www.linkedin.com/in/felipe-dsprado/)

