### Solutions description

There are two branches now:
* master (with naive solution)
* remove_stat (with multitheading approach)

### Used technologies

#### Spring boot

#### Spring Cloud Contract 
  to generate stubs and API unit tests. See test/resources/contracts
  
### Spring Rest Docs 
to generate up to date documentation form unit-tests.
All generated documentation is packed to the jar and can be found on `/docs/index.html` url.

### Maven dependency plugin
to avoid transitive dependencies.

### Maven sort-pom plugin
to keep a standard pom structure.
