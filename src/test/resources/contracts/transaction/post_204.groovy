package contracts.transaction

import java.time.Clock
import java.time.ZonedDateTime


org.springframework.cloud.contract.spec.Contract.make {
    description('''
given: Transaction url
when: User sends an old transaction
then: Server returns 204 No content
''')
    request {
        method 'POST'
        urlPath('/transactions')
        headers {
            header('Content-Type', 'application/json')
        }
        body(
                amount: 20.2,
                timestamp: ZonedDateTime.now(Clock.systemUTC()).minusSeconds(60).toInstant().toEpochMilli()
        )
    }
    response {
        status 204
    }
}