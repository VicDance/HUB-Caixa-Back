# HUB-Caixa-Back

- Introduce a hexagonal architecture using ports and adapters to further decouple
  the domain from infrastructure concerns.
- In this project I decided to use Basic Auth because it is a simple authentication. Oauth2 did not add any value and it was more complex to run.
- This project works with sdk 17 and jaa 17.
- It is important to set the Basic Authorization in postman. For each user the username and password is client/client or manager/manager, depending on which role you are using.
- CreateLoan and GetLoanById need a header with the userId you want to use. Eg.:X-User-id=2a1ab377-5396-4682-b86a-db7bbf07d37c (it is an UUID)