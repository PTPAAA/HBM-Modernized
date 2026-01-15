
import os
import re

ROOT_DIR = r"d:/Documents/GitHub/HBM-Modernized/1.18hbm/targets/mc20forge/src/main/kotlin/dev/ntmr/nucleartech"

ENTITY_VARS = ["entity", "player", "target", "source", "attacker", "victim", "living", "itemEntity", "thrower", "owner"]

def process_file(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    original_content = content
    
    # Fix level access
    for var in ENTITY_VARS:
        # Regex: \bvar\.level\b -> var.level()
        # Be careful not to double replace
        pattern = r"\b" + var + r"\.level\b(?!\()"
        content = re.sub(pattern, var + ".level()", content)
        
    # Fix explode interaction
    content = content.replace("Explosion.BlockInteraction.DESTROY", "Level.ExplosionInteraction.BLOCK")
    content = content.replace("Explosion.BlockInteraction.NONE", "Level.ExplosionInteraction.NONE")
    
    # Fix logic needing Level import if we used Level.ExplosionInteraction
    if "Level.ExplosionInteraction" in content and "import net.minecraft.world.level.Level" not in content:
        # insert import
        lines = content.splitlines()
        for i, line in enumerate(lines):
            if line.strip().startswith("import "):
                 if "import net.minecraft.world.level.Level" not in content: # double check
                     lines.insert(i, "import net.minecraft.world.level.Level")
                     break
        content = "\n".join(lines) + "\n"

    if content != original_content:
        print(f"Modifying {file_path}")
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)

def main():
    for root, dirs, files in os.walk(ROOT_DIR):
        for file in files:
            if file.endswith(".kt"):
                process_file(os.path.join(root, file))

if __name__ == "__main__":
    main()
