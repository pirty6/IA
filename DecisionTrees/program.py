import sys
import math
import collections

def split(data, best_feature):
    feature = collections.OrderedDict()
    index = 0
    sort = 0
    for element in best_feature:
        if element not in feature:
            feature[element] = []
        array = []
        for i in range(len(data[index])):
            array.append(data[index][i])
        feature[element].append(array)
        index += 1
    for element in feature:
        if element in "TRUE" or element in "FALSE":
            sort = 1
            break
    if sort == 1:
        sorted_feature = sorted(feature.keys(), reverse = True)
        sort = collections.OrderedDict()
        for element in sorted_feature:
            sort[element] = feature[element]
        return sort
    return feature


def get_entropy(array):
    dic = collections.OrderedDict()
    entropy = 0
    for entry in array:
        if entry in dic:
            dic[entry] += 1.0
        else:
            dic[entry] = 1.0
    for entry in dic:
        entropy += (-dic[entry]/len(array) * math.log(dic[entry]/len(array), 2.0))
    return entropy

def information_gain(current_feature, result):
    values = collections.OrderedDict()
    index = 0
    ig = get_entropy(result)
    for feature in current_feature:
        if feature in values:
            values[feature].append(result[index])
        else:
            values[feature] = []
            values[feature].append(result[index])
        index += 1
    for key in values:
        ig -= (get_entropy(values[key]) * len(values[key])/len(result))
    return ig


def ID3(data, headers, depth):
    best_ig = 0
    best_feature = []
    result = []
    header = ""
    for i in range(len(data)):
        result.append(data[i][len(data[0]) - 1])
    for i in range(len(data[0]) - 1):
        current_feature = []
        for j in range(len(data)):
            current_feature.append(data[j][i])
        ig = information_gain(current_feature, result)
        if(ig > best_ig):
            best_ig = ig
            best_feature = current_feature
            header = headers[i]
    if best_ig == 0:
        print("  "*depth + "ANSWER: " + result[len(result) - 1])
        return
    split_data = split(data, best_feature)
    # print(split_data)
    for key in split_data:
        print("  "*depth + header + ":", key)
        ID3(split_data[key], headers, depth+1)

data = sys.stdin.readlines()

table = []
attributes = []
add_data = 0

for element in data:
    if "%" in element:
        continue
    if add_data == 1:
        array = element.rstrip('\n').split(',')
        table.append(array)
    if "@attribute" in element:
        attribute = element.split()
        attributes.append(attribute[1])
    if "@data" in element:
        add_data = 1
ID3(table, attributes, 0)
