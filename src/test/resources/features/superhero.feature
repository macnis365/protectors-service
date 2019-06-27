Feature: Testing a REST API
  client should be able to submit POST requests to a endpoint /superhero


  Scenario: superhero data Upload to a web service
    Given the superhero details: the superhero details Clark Kent Superman
    When client upload superhero data
    Then the server should handle it and return a success status