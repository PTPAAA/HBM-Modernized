import re
import os

files = [
    r"d:\Documents\GitHub\HBM-Modernized\1.18hbm\targets\mc20forge\src\main\kotlin\dev\ntmr\nucleartech\content\NTechBlocks.kt",
    r"d:\Documents\GitHub\HBM-Modernized\1.18hbm\targets\mc20forge\src\main\kotlin\dev\ntmr\nucleartech\content\NTechFluids.kt"
]

replacements = [
    (r"Properties\.of\(([A-Z_]+)\)", r"Properties.of().mapColor(MapColor.\1)"),
    (r"Properties\.of\(([A-Z_]+), ([^)]+)\)", r"Properties.of().mapColor(\2)"),
    (r"Material\.", r"MapColor."), # catch specific usages like Material.WATER
    (r"DyeColor\.BLACK", r"MapColor.COLOR_BLACK"),
    (r"DyeColor\.ORANGE", r"MapColor.COLOR_ORANGE"),
]

# Imports to be added if files are modified
extra_imports = [
    "import net.minecraft.world.level.material.MapColor"
]

for file_path in files:
    if os.path.exists(file_path):
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()
        
        original_content = content
        
        # Apply regex replacements
        for pattern, substitution in replacements:
            content = re.sub(pattern, substitution, content)

        # Fix imports: Remove Material wildcard, add MapColor
        content = content.replace("import net.minecraft.world.level.material.Material.*", "")
        # Remove MaterialColor import if it exists, as we likely use MapColor now (MaterialColor was renamed to MapColor in 1.20? Or exists alongside?)
        # Actually MapColor is the new name for MaterialColor.
        content = content.replace("import net.minecraft.world.level.material.MaterialColor", "import net.minecraft.world.level.material.MapColor")
        
        # Only add imports if changed
        if content != original_content:
             # Find package declaration to insert imports after
            lines = content.split('\n')
            last_import_idx = -1
            for i, line in enumerate(lines):
                if line.startswith("import "):
                    last_import_idx = i
            
            if last_import_idx != -1:
                # Check if we already have it
                if "import net.minecraft.world.level.material.MapColor" not in content:
                    lines.insert(last_import_idx + 1, "import net.minecraft.world.level.material.MapColor")
                content = '\n'.join(lines)

        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"Processed {file_path}")
    else:
        print(f"File not found: {file_path}")
