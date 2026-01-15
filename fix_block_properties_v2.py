import re
import os

files = [
    r"d:\Documents\GitHub\HBM-Modernized\1.18hbm\targets\mc20forge\src\main\kotlin\dev\ntmr\nucleartech\content\NTechBlocks.kt",
    r"d:\Documents\GitHub\HBM-Modernized\1.18hbm\targets\mc20forge\src\main\kotlin\dev\ntmr\nucleartech\content\NTechFluids.kt"
]

replacements = [
    (r"\.color\(", ".mapColor("),
    (r"MaterialColor\.", "MapColor.")
]

for file_path in files:
    if os.path.exists(file_path):
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()
        
        for pattern, substitution in replacements:
            content = re.sub(pattern, substitution, content)

        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"Processed {file_path}")
    else:
        print(f"File not found: {file_path}")
