//
// Created by Alexey Shtanko on 11/1/17.
//

#include <fstream>
#include <iostream>
#include "PropertiesParser.h"
#include "PropertiesUtils.h"

namespace cppproperties {

    PropertiesParser::PropertiesParser() {
    }

    PropertiesParser::~PropertiesParser() {
    }

    Properties PropertiesParser::Read(const std::string& file) {
        Properties properties;

        std::ifstream is;
        is.open(file.c_str());
        if (!is.is_open()) {
            std::string msg = "Unable to read " + file;
            throw PropertiesException(msg.c_str());
        }

        try {
            std::string line;
            while (getline(is, line)) {
                if (PropertiesUtils::IsEmptyLine(line) || PropertiesUtils::IsComment(line)) {
                    // ignore it
                } else if (PropertiesUtils::IsProperty(line)) {
                    std::pair<std::string, std::string> prop = PropertiesUtils::ParseProperty(line);
                    properties.AddProperty(prop.first, prop.second);
                } else {
                    std::string msg = "Invalid line: " + line;
                    throw PropertiesException(msg.c_str());
                }
            }
            is.close();
        } catch (...) {
            // don't forget to close the ifstream
            is.close();
            throw;
        }

        return properties;
    }

    void PropertiesParser::Write(const std::string& file, const Properties& props) {
        std::ofstream os;
        os.open(file.c_str());
        if (!os.is_open()) {
            std::string msg = "Unable to write " + file;
            throw PropertiesException(msg.c_str());
        }

        try {
            const std::vector<std::string>& keys = props.GetPropertyNames();
            for (std::vector<std::string>::const_iterator i = keys.begin();
                 i != keys.end(); ++i) {
                os << *i << " = " << props.GetProperty(*i) << std::endl;
            }
            os.close();
        } catch (...) {
            // don't forget to close the ofstream
            os.close();
            throw;
        }
    }

} /* namespace cppproperties */
