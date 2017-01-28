#!/usr/bin/env bats

@test "addition using bc" {
    skip
    result="$(echo 2+2 | bc)"
    [ "$result" -eq 4 ]
}

@test "addition using dc" {
    skip
    result="$(echo 2 2+p | dc)"
    [ "$result" -eq 4 ]
}
