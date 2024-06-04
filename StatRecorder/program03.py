# program03.py

print('\nRequirement 1\n')
print('This is Program 3 - Christian Garcia\n')

print('Requirement 2\n')
print('This program records goals for soccer players and points for basketball players.\n')

print('Requirement 3\n')
player_names = []
for i in range(3):
    player_name = input("Enter the name of soccer player {}: ".format(i+1))
    player_names.append(player_name)

print('\nRequirement 4\n')
career_goals = []
for player_name in player_names:
    goals = int(input("Enter the number of career goals for {}: ".format(player_name)))
    career_goals.append(goals)

print('\nRequirement 5\n')
print('Sorting the order of the players by career goals\n')
num_players = len(player_names)

for i in range(num_players - 1):
    max_idx = i
    for j in range(i + 1, num_players):
        if career_goals[j] > career_goals[max_idx]:
            max_idx = j

    # Swap the players and goals to move the player with the most goals to the front
    player_names[i], player_names[max_idx] = player_names[max_idx], player_names[i]
    career_goals[i], career_goals[max_idx] = career_goals[max_idx], career_goals[i]
print('Requirement 6\n')
print('Sorted Player Names and Career Goals:')
i = 0
for i in range(len(player_names)):
    print('\t',i+1,'{} - {}'.format(player_names[i], career_goals[i]))
    i += 1

print('\nRequirement 7\n')
basketball_players = []
for i in range(3):
    player_name = input("Enter the name of basketball player {}: ".format(i+1))
    basketball_players.append(player_name)

print('\nRequirement 8\n')
career_points = []
for player_name in basketball_players:
    points = int(input("Enter the number of career points for {}: ".format(player_name)))
    career_points.append(points)

print('\nRequirement 9\n')
print('Sorting the order of the players by career points\n')
num_players = len(basketball_players)

for i in range(num_players - 1):
    max_idx = i
    for j in range(i + 1, num_players):
        if career_points[j] > career_points[max_idx]:
            max_idx = j

    # Swap the players and points to move the player with the most points to the front
    basketball_players[i], basketball_players[max_idx] = basketball_players[max_idx], basketball_players[i]
    career_points[i], career_points[max_idx] = career_points[max_idx], career_points[i]

print('Requirement 10\n')
print('Sorted Player Names and Career Points:')
for i in range(len(basketball_players)):
    print('\t',i+1,'{} - {}'.format(basketball_players[i], career_points[i]))
    i += 1

print('\nRequirement 11\n')  
print('''My experience with this project was good, it was fairly easy to put together once I knew
what I had to do to sort the goals/points in descending order. One of the hardest parts for me in 
this program was formatting the print statement that shows the sorted order. ''')
