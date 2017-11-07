cmake_minimum_required(VERSION 3.4.1)

project(picasa-lib)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++14")

add_library( picasa-lib
             SHARED
             main.cpp
             Properties.cpp
             Properties.h
             PropertiesParser.cpp
             PropertiesParser.h
             PropertiesUtils.h
             PropertiesUtils.cpp)

find_library( log-lib
              log )

target_link_libraries( picasa-lib
                       ${log-lib} )