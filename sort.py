import os
import shutil

current_directory = os.getcwd()
new_directory = os.path.join(current_directory, 'sort')

if not os.path.exists(new_directory):
    os.makedirs(new_directory)

for filename in [filename for filename in os.listdir(current_directory) if filename.endswith('.txt')]:
    with open(filename, 'r') as file:
        lines = file.readlines()
    
    lines = [line.rstrip('\n') for line in lines]
    
    new_file_path = os.path.join(new_directory, filename)
    
    with open(new_file_path, 'w') as new_file:
        new_file.write('\n'.join(sorted(lines, key=lambda line: len(line))))
    
    print(f"File '{filename}' sorted and saved as '{new_file_path}'")
