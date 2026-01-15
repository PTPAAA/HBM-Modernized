
import os
import re

ROOT_DIR = r"d:/Documents/GitHub/HBM-Modernized/1.18hbm/targets/mc20forge/src/main/kotlin/dev/ntmr/nucleartech"

REPLACEMENTS = [
    (r"import net\.minecraftforge\.energy\.CapabilityEnergy", r"import net.minecraftforge.common.capabilities.ForgeCapabilities"),
    (r"import net\.minecraftforge\.items\.CapabilityItemHandler", r"import net.minecraftforge.common.capabilities.ForgeCapabilities"),
    (r"import net\.minecraftforge\.fluids\.capability\.CapabilityFluidHandler", r"import net.minecraftforge.common.capabilities.ForgeCapabilities"),
    
    (r"CapabilityEnergy\.ENERGY", "ForgeCapabilities.ENERGY"),
    (r"CapabilityItemHandler\.ITEM_HANDLER_CAPABILITY", "ForgeCapabilities.ITEM_HANDLER"),
    (r"CapabilityFluidHandler\.FLUID_HANDLER_CAPABILITY", "ForgeCapabilities.FLUID_HANDLER"),
    (r"CapabilityFluidHandler\.FLUID_HANDLER_ITEM_CAPABILITY", "ForgeCapabilities.FLUID_HANDLER_ITEM"),
]

# Ensure we have the interface imports if we use the capabilities
INTERFACE_IMPORTS = {
    "ForgeCapabilities.ENERGY": "import net.minecraftforge.energy.IEnergyStorage",
    "ForgeCapabilities.ITEM_HANDLER": "import net.minecraftforge.items.IItemHandler",
    "ForgeCapabilities.FLUID_HANDLER": "import net.minecraftforge.fluids.capability.IFluidHandler",
    "ForgeCapabilities.FLUID_HANDLER_ITEM": "import net.minecraftforge.fluids.capability.IFluidHandlerItem",
}

def process_file(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    original_content = content
    
    for pattern, replacement in REPLACEMENTS:
        content = re.sub(pattern, replacement, content)
    
    # Add missing interface imports if needed
    lines = content.splitlines()
    last_import_idx = -1
    for i, line in enumerate(lines):
        if line.strip().startswith("import "):
            last_import_idx = i
            
    if last_import_idx != -1:
        needed_imports = set()
        for cap, imp in INTERFACE_IMPORTS.items():
            if cap in content and imp not in content:
                # Basic check, might be imported with * or full path, but this helps strict imports
                 if imp.split(" ")[1] not in content: # Check if class is mentioned? No, check if import string exists
                     needed_imports.add(imp)

        if needed_imports:
            # check if we already have them (basic check)
            for imp in needed_imports:
                if imp not in content:
                    lines.insert(last_import_idx + 1, imp)
            content = "\n".join(lines) + "\n" # restore newline

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
