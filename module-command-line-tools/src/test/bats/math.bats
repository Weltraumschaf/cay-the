#!/usr/bin/env bats

@test "add 2 and 3 must be 5" {
    skip
    result="$(${SUT} run -f ${FIXTURE_DIR}/addition.ct)"
    [ "$result" -eq 5 ]
}

@test "faculty of 3 must be 6" {
    skip
    result="$(${SUT} run -f ${FIXTURE_DIR}/faculty.ct)"
    [ "$result" -eq 6 ]
}