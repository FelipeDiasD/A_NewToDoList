# Uma Nova To Do List


## Conteúdo

- [Visão geral](#visão-geral)
    - [A applicação](#a-aplicação)
    - [Screenshot](#screenshot)
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
Uma aplicação web desenvolvida utilizando Java e o framework Springboot.




### Screenshot
TODO
### Links


- URL do deploy: [Em breve](https://your-live-site-url.com)

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
da senha do usuário no banco de dados. 


### Próximos passos



### Recursos úteis

## Autor

- Website(GitHub) - [Felipe Dias](https://www.your-site.com)
- LinkedIn - [Felipe Dias](https://www.linkedin.com/in/felipe-dsprado/)

