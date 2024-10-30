from pypdf import PdfReader 
import re
from pathlib import Path
import threading
from flask import Flask, render_template

app = Flask(__name__)

@app.route('/')
def index():
    return render_template('index.html', output_data=output)


def iterate():
    list = Path('BiQue').rglob('*.pdf')
    for _ in list:
        getqs(str(_))


definitioncases = [
    # "muscle",
    # "sarco",
    # "afferent",
    # "efferent",
    # "contraction",
    # "myos",
    # "filament",
    # "resting",

    # "state",
    # "ADH",
    # "kidney",
    # "myosin",
    # "muscle contraction",
    # "nephron",
    # "kidney",
    ]

cellular_and_molecular_biology = [
    "cell", "organelle", "membrane", "nucleus", "mitochondria", "ribosome",
    "endoplasmic", "golgi", "cytoskeleton", "chromosome", "gene", "mutation",
    "enzyme", "substrate", "active", "site", "atp", "photosynthesis", 
    "respiration", "dna", "rna", "transcription", "translation", 
    "genotype", "phenotype"
]

genetics_and_evolution = [
    "inheritance", "mendel", "genotype", "phenotype", "allele", "gene",
    "mutation", "genetic", "disorder", "genetic", "variation", "selection",
    "adaptation", "speciation", "evolution", "population", "genetics"
]

physiology = [
    "organ", "system", "homeostasis", "feedback", "circulatory", "respiratory",
    "digestive", "excretory", "neural", "hormone", "plant", "photosynthesis",
    "transport", "stomata", "xylem", "phloem", "kidney", "heart", "lungs"
]

ecology = [
    "ecosystem", "biodiversity", "energy", "nutrient", "cycle", "population",
    "community", "habitat", "niche", "biome", "conservation", "pollution",
    "succession", "interactions", "adaptation", "species", "biotic", "abiotic"
]

biotechnology_and_genetic_engineering = [
    "pcr", "gel", "electrophoresis", "cloning", "recombinant", "dna",
    "transgenic", "vector", "gene", "therapy", "restriction", "enzyme",
    "genetic", "modification", "sequencing", "biotechnology"
]

investigative_skills_and_practical_work = [
    "experiment", "data", "analysis", "method", "variable", "control",
    "technique", "accuracy", "precision", "measurement", "result", "procedure",
    "observation", "hypothesis", "conclusion", "interpretation", "quantitative",
    "qualitative"
]

control_and_coordination_keywords = [
    "endocrine", "hormones", "adh", "glucagon", "insulin",
    "nervous", "system", "sensory", "neurone", "motor", 
    "intermediate", "receptor", "stimuli", "impulse", 
    "action", "potential", "chemoreceptor", "taste", 
    "membrane", "potential", "resting", "action", 
    "refractory", "myelinated", "saltatory", "conduction",
    "refractory", "frequency", "cholinergic", "synapse", 
    "calcium", "ions", "neuromuscular", "junctions", 
    "t-tubule", "system", "sarcoplasmic", "reticulum", 
    "muscle", "ultrastructure", "sarcomere", "electron", 
    "micrographs", "sliding", "filament", "contraction", 
    "troponin", "tropomyosin", "calcium", "atp"
]

homeostasis_keywords = [
    "homeostasis", "internal", "external", "stimuli", 
    "receptors", "coordination", "nervous", "system", "endocrine", 
    "effectors", "muscles", "glands", "negative", "feedback", 
    "urea", "liver", "deamination", "amino", "acids", "kidney",
    "capsule", "cortex", "medulla", "renal", "pelvis", 
    "ureter", "artery", "vein", "nephron", "glomerulus", 
    "bowman's", "capsule", "proximal", "convoluted", "tubule", 
    "loop", "henle", "distal", "collecting", "duct", 
    "urine", "formation", "filtrate", "ultrafiltration", 
    "selective", "reabsorption", "hypothalamus", "posterior", 
    "pituitary", "antidiuretic", "adh", "aquaporins", 
    "osmoregulation", "signalling", "glucose", "glucagon", 
    "receptor", "conformational", "g-protein", "adenylyl", 
    "cyclase", "messenger", "cyclic", "cAMP", "kinase", "cascade", "phosphorylation", 
    "cellular", "response", "glycogen", "negative", "feedback", 
    "insulin", "muscle", "liver", "strips", "biosensors", 
    "glucose", "oxidase", "peroxidase", 
]

definitioncases = homeostasis_keywords

alph = ['a', 'b', 'c', 'd', 'e', 'f', 'g']
roman = ['i', 'ii', 'iii', 'iv', 'v', 'vi', 'vii', 'viii']
arab = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 
        17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 
        31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 
        45, 46, 47, 48, 49, 50]

output=[]


def findhaha(mat,w):
    pattern = rf'\(.*?\).+?{re.escape(mat)}.*$'
    return re.search(pattern,w,re.IGNORECASE | re.MULTILINE)

def findhaha3(mat,w):
    stupid = "......................................"
    pattern = rf'\n.+(\(.*\).+?{re.escape(mat)}.+?){re.escape(stupid)}' # this is starts with the thingt, because spaces
    return re.findall(pattern,w,re.IGNORECASE | re.MULTILINE | re.DOTALL) # this way is bad because it believes context to be the question
   #fixed, goo job me
   #check that first braket has no CAPS or numb3rs
def getpart(w):
    extraction_pattern = rf'\((.*)\)'
    return re.findall(extraction_pattern,w,re.IGNORECASE | re.MULTILINE)


def getquest(hello):
    pattern1 = rf'^\d+.*?\[Total: \d+\]*$'
    return re.findall(pattern1,hello,re.IGNORECASE | re.MULTILINE | re.DOTALL)

def getsubpart(j,deep,wholething):
    myguy = str(getquest(wholething)[j])
    # print(myguy.find('('+deep+')'))
    arkansas = myguy.find("("+deep+")")
    while True:
        arkansas-=1
        if myguy[arkansas] == '(' and myguy[arkansas+2] == ')' and myguy[arkansas+1] in alph:
            return myguy[arkansas+1]
        
def getansa(byebye,haha):
    pattern = rf'{re.escape(haha)}(.*?)\s*;\s*(\d+)\s*(?:\n|(?=9700))' #still a case, just do braindead
    return re.search(pattern, byebye, re.DOTALL |re.IGNORECASE | re.MULTILINE)

# def getansa2(byebye,haha):
#     pattern = rf'({re.escape(haha)})(.*?)(?=\n\d+(\([a-z]\))?(\([ivxlc]+\))?)'
#     return re.search(pattern, byebye, re.DOTALL |re.IGNORECASE | re.MULTILINE)

def getansa2(byebye,haha):
    pattern = rf'({re.escape(haha)})(.*?)(?=\n\d+(\([a-z]\))?(\([ivxlc]+\))?(\d|\([a-z]\)|\([ivxlc]+\)))'
    return re.search(pattern, byebye, re.DOTALL |re.IGNORECASE | re.MULTILINE)

def containstheshit(jaja):
    pata = rf'^(.+?)(?=9700)'
    if re.search(pata, jaja, re.DOTALL |re.IGNORECASE | re.MULTILINE):

        return re.search(pata, jaja, re.DOTALL |re.IGNORECASE | re.MULTILINE).group(0)
    
    else:
        return jaja

def getraw(textmepleaseibegyouimissyou,part,subpart):
    if not subpart:
        if part:
            patter = rf'{re.escape(part)}\s*(.+)$'
        else: 
            patter = rf'\(.*\)\s*(.+)$'
    if subpart: 
        patter = rf'{re.escape(subpart)}\s*(.+)$'
    
    return re.search(patter, textmepleaseibegyouimissyou, re.DOTALL |re.IGNORECASE | re.MULTILINE)


def getqs(paper):
    reader = PdfReader(paper) 
    modifications = [(2, 'A'), (3, 'n'), (4, 's'), (15, 'm'), (16, 's')]

    anspaper = ''.join(
        (char if i not in dict(modifications) else dict(modifications)[i])
        for i, char in enumerate(paper))

    wholething = ""
    for _ in reader.pages:
        wholething +=(_.extract_text() + "\n")

    for i in range(len(reader.pages)):
        page = reader.pages[i]
        text=page.extract_text()
        for _ in definitioncases:
            if findhaha(_,text):
                for j in range(len(getquest(wholething))):
                    if findhaha(_,text).group(0) in getquest(wholething)[j]:
                        questno = str(j+1)
                        subpartno = ""
                        partno = ""
                        if getpart(findhaha(_,text).group(0))[-1] in roman:
                            partno = "("+getsubpart(j,getpart(findhaha(_,text).group(0))[-1],wholething)+")"
                            subpartno = "("+getpart(findhaha(_,text).group(0))[-1]+")"
                        else:
                            partno = "("+getpart(findhaha(_,text).group(0))[-1]+")"
                        
                        # print(findhaha3(_,text)[0])
                        try:
                            temp=str(""+getraw(findhaha3(_,text)[0],partno,subpartno).group(1))
                            temp2=str(getas(anspaper,questno + partno + subpartno))
                            # print("-"*100+"\nQ: "+getraw(findhaha3(_,text)[0]).group(1))
                            # # print("FOUND ON: pg."+ str(i +1) + ", q."+ questno + partno + subpartno)
                            # print(getas(anspaper,questno + partno + subpartno))
                            # print("-"*100+"\n")
                            output.append([temp,temp2,anspaper[5:]])
                            
                        except:
                            print("FAILURE")
                            pass
                            # print("EPIC FAIL ON: " + questno + partno + subpartno)

#matches this ...........................................................................................................................................

def getas(paper,id):
    reader1 = PdfReader(paper) 

    wholething1 = ""
    for _ in reader1.pages:
        wholething1 +=(_.extract_text() )
    try:
        return str("" +containstheshit(getansa2(wholething1,id).group(2)))
    except:
        return str("A: FAILED ON: (" +paper+") " )
    # print(wholething1)


async def asleep():
    pass

data_thread = threading.Thread(target=iterate)
data_thread.daemon = True
data_thread.start()
                

if __name__=="__main__":
    # iterate()
    # getqs('ChQue/9700_s18_qp_41.pdf')
    app.run(debug=True)
    