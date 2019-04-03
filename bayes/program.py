import sys
from copy import deepcopy

def get_ancestors(query, table, ancestors, parents):
    if len(parents) == 0:
        for element in query:
            parents.append(element)
    if len(query) != 0:
        parent = table[query[0]]['ancestors']
        for ancestor in parent:
            if_exists(ancestor, parents, ancestors, query)
        return get_ancestors(query[1:], table, ancestors, parents)
    else:
        return ancestors

def if_exists(a, p, ans, q):
    if a not in ans and a not in p:
        ans.append(a)
        if a not in q:
            q.append(a)

def enumerate(probabilities, start, end, combinations, sign, index):
    if index < len(probabilities):
        condition = deepcopy(probabilities[index])
        for j in range (start,end):
            combinations[j][index] = sign + condition
            enumerate(probabilities, start, start + int((end-start)/2), combinations, '+', index+1)
            enumerate(probabilities, start + int((end-start)/2), end, combinations, '-', index+1)

def enumerate_all(probabilities):
    num_combinations = 2 ** len(probabilities)
    combinations = []
    for i in range (0, num_combinations):
        combinations.append(deepcopy(probabilities))
    enumerate(probabilities, 0, int(num_combinations/2), combinations, '+', 0)
    enumerate(probabilities, int(num_combinations/2), num_combinations, combinations, '-', 0)
    return combinations


def get_probability(table, elem):
    val = 1.0
    for item in elem:
        expression = []
        node = table[item[1:]]
        condition = item
        for ancestor in node['ancestors']:
            for element in elem:
                get_ancestor(ancestor, expression, element)
        expression = [condition] + expression
        val = get_value(probability, node['probabilities'], expression, val)
    return val

def get_value(p, n, e, v):
    for p in n:
        if p[0:-1] == e:
            v *= p[-1]
    return v

def get_ancestor(a, expression, e):
    if a == e[1:]:
        expression.append(e)

def append_probabilities(prob, sign, n, ps):
    n[0] = sign + prob[0][1:]
    n[len(n)-1] = round(1 - prob[len(n) - 1], 7)
    if n not in ps:
        ps.append(n)

# Read input
data = sys.stdin.readlines()
# Get nodes
nodes = data[0].rstrip("\n").split(",")
table = {}
# Create dictionary for all the probabilities and the ancestors
for i in range(0, len(nodes)):
    table[nodes[i]] = {'probabilities': [], 'ancestors': []}
# Get the probabilities from the given data
no_probabilities = int(data[1].rstrip("\n"))
probabilities = []
# Add the probabilities to the table
for i in range(2, no_probabilities + 2):
    probability = data[i].rstrip("\n").replace('|',',').replace('=',',').split(',')
    key = probability[0][1:]
    length = len(probability) - 1
    probability[length] = float(probability[length])
    table[key]['probabilities'].append(probability)
# Get queries
no_queries = int(data[no_probabilities + 2].rstrip("\n"))
queries = []
for i in range(no_probabilities + 3, no_queries + no_probabilities + 3):
    query = data[i].rstrip("\n")
    conditions = []
    if "|" in query:
        conditions = query.split('|')[1].split(',')
    query = [query.replace('|', ',').split(','), conditions]
    queries.append(query)
# Get all the probabilities and add them to the dictionary
for item in table:
    probabilities = table[item]['probabilities']
    for probability in probabilities:
        sign = probability[0][0]
        if(sign == '+'):
            new_sign = '-'
        else:
            new_sign = '+'
        new_probability = deepcopy(probability)
        append_probabilities(probability, new_sign, new_probability, probabilities)
    # Get the ancestors of the nodes
    ancestors = table[item]['probabilities'][0][1:len(table[item]['probabilities'][0])-1]
    for i in range(0, len(ancestors)):
        ancestors[i] = ancestors[i][1:]
    table[item]['ancestors'] = ancestors
# Get result from the queries
for query in queries:
    # Get all the conditions from the query ex: Ill, Test
    list_conditions = []
    for q in query[0]:
        list_conditions.append(q[1:])
    parents_numerator = []
    ancestors_numerator = get_ancestors(list_conditions, table, [], parents_numerator)
    combinations_numerator = enumerate_all(ancestors_numerator)
    numerator = 0
    for elem in combinations_numerator:
        elem = query[0] + elem
        numerator += get_probability(table, elem)
    if len(query[1]) > 0:
        list_conditions = []
        for q in query[1]:
            list_conditions.append(q[1:])
        parents_denominator = []
        ancestors_denominator = get_ancestors(list_conditions, table, [], parents_denominator)
        combinations_denominator = enumerate_all(ancestors_denominator)
        denominator = 0
        for item in combinations_denominator:
            item = query[1] + item
            denominator += get_probability(table, item)
        print(round(numerator/denominator, 7))
    else:
        print(round(numerator,7))
