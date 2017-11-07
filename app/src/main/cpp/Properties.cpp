//
// Created by Alexey Shtanko on 11/1/17.
//

#include <algorithm>
#include "Properties.h"

namespace cppproperties {
    Properties::Properties() {
    }

    Properties::~Properties() {
    }

    std::string Properties::GetProperty(const std::string& key) const {
        if (properties.find(key) == properties.end()) {
            std::string msg = key + " does not exist";
            throw PropertyNotFoundException(msg.c_str());
        }
        return properties.at(key);
    }

    std::string Properties::GetProperty(const std::string& key, const std::string& defaultValue) const {
        if (properties.find(key) == properties.end()) {
            return defaultValue;
        }
        return properties.at(key);
    }

    std::vector<std::string> Properties::GetPropertyNames() const {
        return keys;
    }

    void Properties::AddProperty(const std::string& key, const std::string& value) {
        if (properties.find(key) == properties.end()) {
            keys.push_back(key);
        }
        properties[key] = value;
    }

    void Properties::RemoveProperty(const std::string& key) {
        if (properties.find(key) == properties.end()) {
            std::string msg = key + " does not exist";
            throw PropertyNotFoundException(msg.c_str());
        }
        keys.erase(std::remove(keys.begin(), keys.end(), key), keys.end());
        properties.erase(key);
    }
}