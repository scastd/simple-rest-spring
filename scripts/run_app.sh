#!/bin/bash

#######################################
# Exports the environment variables from the given file
# Arguments:
#   1 - the file to be read
#######################################
function export_env_variables() {
  while IFS= read -r line; do
    export "$line"
  done < "$1"
}

#######################################
# Main function
# Arguments:
#  None
#######################################
function main() {
  export_env_variables env/.env

  ./mvnw package -DskipTests
  java -jar target/rest-spring-template-0.0.1.jar
}

# Run the main function
main "$@"
