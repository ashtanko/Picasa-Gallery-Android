//
// Created by Alexey Shtanko on 11/1/17.
//

#ifndef PICASAGALLERYANDROID_PROPERTIESPARSER_H
#define PICASAGALLERYANDROID_PROPERTIESPARSER_H

#include <string>
#include <exception>
#include "Properties.h"

namespace cppproperties {

    class PropertiesParser {
    public:
        PropertiesParser();
        virtual ~PropertiesParser();

        /**
         * Reads a properties file and returns a Properties object.
         */
        static Properties Read(const std::string& file);

        /**
         * Writes Properties object to a file.
         */
        static void Write(const std::string& file, const Properties& props);
    };

} /* namespace cppproperties */



#endif //PICASAGALLERYANDROID_PROPERTIESPARSER_H
