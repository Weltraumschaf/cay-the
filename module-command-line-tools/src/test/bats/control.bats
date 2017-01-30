#!/usr/bin/env bats

@test "loop must be executed 4 times" {
    skip
    result="$(${SUT} run -f ${FIXTURE_DIR}/endless_loop.ct)"
    echo "#### $result"
}