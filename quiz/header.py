try:
    with open('JAVA_75-Spring.txt', 'r') as source_file, open('JAVA_75-Spring-ru.txt', 'r+') as target_file:
        source_lines = source_file.readlines()
        target_lines = target_file.readlines()
        
        for i in range(min(len(source_lines), len(target_lines))):
            if '\t' in source_lines[i]:
                target_lines[i] = source_lines[i].split('\t')[0] + '\t' + target_lines[i].split('\t')[1]
        
        target_file.seek(0)
        target_file.writelines(target_lines)
        target_file.truncate()
        
    print("File modifications completed successfully.")
except FileNotFoundError:
    print("One or both files were not found.")
except Exception as e:
    print("An error occurred while processing the files:", str(e))
