package contracts.transaction


org.springframework.cloud.contract.spec.Contract.make {
    description('''
given: Transaction url
when: User sends an old transaction
then: Server returns 201
''')
    request {
        method 'POST'
        urlPath('/transactions')
        headers {
            header('Content-Type', 'application/json')
        }
        body('''
            {
              "amount": 20.1,
              "timestamp":1520000000
              
            }
            '''
        )
    }
    response {
        status 204
    }
}