cmake_minimum_required(VERSION 3.4.1)

project(picasa)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++14")

add_library( picasa
             SHARED
             main.cpp )

find_library( log-lib
              log )

target_link_libraries( picasa
                       ${log-lib} )