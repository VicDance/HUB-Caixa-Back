# HUB-Caixa-Back

- Introduce a hexagonal architecture using ports and adapters to further decouple
  the domain from infrastructure concerns.
- In this project I decided to use Basic Auth because it is a simple authentication. Oauth2 did not add any value and it was more complex to run.
- This project works with sdk 17 and jaa 17.
- It is important to set the Basic Authorization in postman. For each user the username and password is client/client or manager/manager, depending on which role you are using.
- CreateLoan and GetLoanById need a header with the userId you want to use. Eg.:X-User-id=2a1ab377-5396-4682-b86a-db7bbf07d37c (it is an UUID)


## Future Improvements
- Include docker if you want the application to go to production.
- Change the authorization to be a stronger one, like OAuth2.
- Right now the implemented errors are basic ones, but further descriptive ones could be included.
- More validation errors like what would happen if the amount you pass is negative?


## Executing the project
- To execute the project you have to go to the main class and click run. 