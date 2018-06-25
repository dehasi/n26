package contracts.transaction

import java.time.Clock
import java.time.ZonedDateTime


org.springframework.cloud.contract.spec.Contract.make {
    description('''
given: Transaction url
when: User sends a transaction
then: Server returns 201 Created
''')
    request {
        method 'POST'
        urlPath('/transactions')
        headers {
            header('Content-Type', 'application/json')
        }
        body(
                amount: 20.2,
                timestamp: ZonedDateTime.now(Clock.systemUTC()).plusSeconds(61).toInstant().toEpochMilli()
        )
    }
    response {
        status 201
    }
}