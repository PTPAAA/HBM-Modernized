
import os
import re

ROOT_DIR = r"d:/Documents/GitHub/HBM-Modernized/1.18hbm/targets/mc20forge/src/main/kotlin/dev/ntmr/nucleartech"

REPLACEMENTS = [
    (r"import com\.mojang\.math\.Vector3f", r"import org.joml.Vector3f"),
    (r"import com\.mojang\.math\.Vector4f", r"import org.joml.Vector4f"),
    (r"import com\.mojang\.math\.Vector3d", r"import org.joml.Vector3d"),
    (r"import com\.mojang\.math\.Matrix4f", r"import org.joml.Matrix4f"),
    (r"import com\.mojang\.math\.Matrix3f", r"import org.joml.Matrix3f"),
    (r"import com\.mojang\.math\.Quaternion", r"import org.joml.Quaternionf"),
    
    # Class usage replacements (if they were imported)
    (r"\bQuaternion\b", "Quaternionf"),
    (r"\bTransmission\b", "Transformation") # Optional check?
]

def process_file(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    original_content = content
    
    for pattern, replacement in REPLACEMENTS:
        content = re.sub(pattern, replacement, content)
        
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
