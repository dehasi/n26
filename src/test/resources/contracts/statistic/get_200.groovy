package contracts.statistic


org.springframework.cloud.contract.spec.Contract.make {
    description('''
given: Statistics url
when: User requests statistics
then: Server returns 200 and statistics in the body
''')
    request {
        method 'GET'
        urlPath('/statistics')
    }
    response {
        status 200

        body('''
          {
            "sum": 0,
            "avg": 0,
            "max": 0,
            "min": 0,
            "count": 0
          }
          '''
        )
    }
}