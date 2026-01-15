import re
import os

files = [
    r"d:\Documents\GitHub\HBM-Modernized\1.18hbm\targets\mc20forge\src\main\kotlin\dev\ntmr\nucleartech\content\NTechItems.kt",
    r"d:\Documents\GitHub\HBM-Modernized\1.18hbm\targets\mc20forge\src\main\kotlin\dev\ntmr\nucleartech\content\NTechBlockItems.kt"
]

pattern = re.compile(r"\.tab\(CreativeTabs\.[a-zA-Z]+\)")

for file_path in files:
    if os.path.exists(file_path):
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()
        
        new_content = pattern.sub("", content)
        
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(new_content)
        print(f"Processed {file_path}")
    else:
        print(f"File not found: {file_path}")
