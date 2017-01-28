#!/usr/bin/env bats

@test "add 2 and 3 must be 5" {
    result="$(${SUT} run -f ${FIXTURE_DIR}/addition.ct)"
    echo "R: $result"
    [ "$result" -eq 5 ]
}