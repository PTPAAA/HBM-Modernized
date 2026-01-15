
import os
import re

ROOT_DIR = r"d:/Documents/GitHub/HBM-Modernized/1.18hbm/targets/mc20forge/src/main/kotlin/dev/ntmr/nucleartech"

def process_file(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    original_content = content
    
    # helper for checking strict word boundary for type replacement
    # We want to match "TextComponent" but NOT "TextComponent("
    
    # 1. EMPTY
    content = content.replace("TextComponent.EMPTY", "Component.empty()")
    
    # 2. Factories
    content = re.sub(r"TextComponent\(", "Component.literal(", content)
    content = re.sub(r"TranslatableComponent\(", "Component.translatable(", content)
    
    # 3. Type usages (TextComponent -> MutableComponent)
    # Match TextComponent where NOT followed by ( or .literal or .empty (already replaced)
    # Using negative lookahead
    # Matches "TextComponent" not followed by (
    content = re.sub(r"TextComponent(?!\()", "MutableComponent", content)
    content = re.sub(r"TranslatableComponent(?!\()", "MutableComponent", content)
    
    # 4. Imports
    # Remove old imports, ensure Component/MutableComponent imports exist if needed
    lines = content.splitlines()
    new_lines = []
    
    has_component_import = False
    has_mutable_import = False
    last_import_idx = -1
    
    for i, line in enumerate(lines):
        if line.strip().startswith("import "):
            last_import_idx = i
            if "net.minecraft.network.chat.Component" in line:
                has_component_import = True
            if "net.minecraft.network.chat.MutableComponent" in line:
                has_mutable_import = True
            
            # Remove the old imports
            if "net.minecraft.network.chat.TextComponent" in line:
                continue
            if "net.minecraft.network.chat.TranslatableComponent" in line:
                continue
        
        new_lines.append(line)
        
    # check if we used Component or MutableComponent and insert imports
    # Simple check: if "Component." in content or "MutableComponent" in content
    uses_component = "Component." in content or "MutableComponent" in content
    
    if uses_component:
        if not has_component_import:
             # Insert after last import
             if last_import_idx != -1:
                 new_lines.insert(last_import_idx+1, "import net.minecraft.network.chat.Component")
                 last_import_idx += 1
                 has_component_import = True
        if "MutableComponent" in content and not has_mutable_import:
             if last_import_idx != -1:
                 new_lines.insert(last_import_idx+1, "import net.minecraft.network.chat.MutableComponent")

    content = "\n".join(new_lines) + "\n"

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
