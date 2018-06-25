package contracts.transaction


org.springframework.cloud.contract.spec.Contract.make {
    description('''
given: Transaction url
when: User sends a transaction
then: Server returns 204
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
              "timestamp":1529939999999
              
            }
            '''
        )
    }
    response {
        status 204
    }
}