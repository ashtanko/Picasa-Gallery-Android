//
// Created by Alexey Shtanko on 11/1/17.
//

#ifndef PICASAGALLERYANDROID_PROPERTIESUTILS_H
#define PICASAGALLERYANDROID_PROPERTIESUTILS_H

#include <string>
#include <utility>

namespace cppproperties {
    namespace PropertiesUtils {

/**
 * Left trims a string.
 * This function doesn't modify the given str.
 */
        std::string LeftTrim(const std::string& str);

/**
 * Right trims a string.
 * This function doesn't modify the given str.
 */
        std::string RightTrim(const std::string& str);

/**
 * Trims a string (perform a left and right trims).
 * This function doesn't modify the given str.
 */
        std::string Trim(const std::string& str);

/**
 * Is a given string a property. A property must have the following format:
 * key=value
 */
        bool IsProperty(const std::string& str);

/**
 * Parses a given property into a pair of key and value.
 *
 * ParseProperty assumes a given string has a correct format.
 */
        std::pair<std::string, std::string> ParseProperty(const std::string& str);

/**
 * Is a given string a comment? A comment starts with #
 */
        bool IsComment(const std::string& str);

/**
 * Is a given string empty?
 */
        bool IsEmptyLine(const std::string& str);

    } // namespace PropertiesUtils
} // namespace cppproperties

#endif //PICASAGALLERYANDROID_PROPERTIESUTILS_H
