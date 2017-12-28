#!/usr/bin/env bats

@test "endless loop must be executed 4 times" {
    skip
    result="$(${SUT} run -f ${FIXTURE_DIR}/endless_loop.ct)"
    [ "$result" -eq 4 ]
}

@test "conditional loop must be executed 4 times" {
    skip
    result="$(${SUT} run -f ${FIXTURE_DIR}/conditional_loop.ct)"
    [ "$result" -eq 4 ]
}

@test "tarditional loop must be executed 4 times" {
    skip
    result="$(${SUT} run -f ${FIXTURE_DIR}/traditional_loop.ct)"
    [ "$result" -eq 4 ]
}