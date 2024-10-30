import json
import requests
import threading
import time
from flask import Flask, render_template

import asyncio
import aiohttp
from datetime import datetime, timedelta
import pygame

pygame.mixer.init()

sound_file = "static/sounds/ding-36029.mp3"
pygame.mixer.music.load(sound_file)

sillycounter = 0

async def get_duration_until_api_update():
    num_attempts = 0
    while True:
        num_attempts += 1

        async with aiohttp.ClientSession() as session:
            try:
                async with session.get("https://api.hypixel.net/skyblock/auctions?page=0") as response:
                    if "age" in response.headers:
                        age = int(response.headers["age"])
                        time_left = 60 - age + 2

                        if time_left > 120:
                            await asyncio.sleep(15)
                            continue

                        return time_left

                    else:
                        return 60 

            except aiohttp.ClientError:
                pass

        await asyncio.sleep(15)

app = Flask(__name__)

API_KEY = '64bd424e-ccb0-42ed-8b66-6e42a135afb4'
FILTERED_ITEM_NAMES = ["Crimson", "Molten", "Terror", "Hollow", "Fervor", "Aurora", "Silver Fang", "Zombie Soldier",
                       "Skeleton Master", "Dreadlord", "Skeleton Soldier", "Mithril Coat", "Mineral", "Machine Gun",
                       "Emerald Blade", "Aspect of the End", "Skeletor", "Lapis Armor", "Thunder Helmet",
                       "Combat Exp Boost", "Miner's Outfit", "Conjuring", "Nutcracker", "Glacite", "Heavy",
                       "Edible Mace", "Raider Axe", "Young Dragon", "Bouncy", "Zombie Knight", "Mining Exp Boost",
                       "Unstable Dragon", "Thunder", "Slug Boots", "Goblin", "Magma Lord", "Moogma", "Final Destination",
                        "Soul Whip"]

def load_data(filename):
    try:
        with open(filename, 'r') as file:
            return json.load(file)
    except FileNotFoundError:
        return {}


def save_data(data, filename):
    with open(filename, 'w') as file:
        json.dump(data, file)


uuidlist = load_data("uuidlist.json")
lbinlist = load_data("lbinlist.json")





def get_my_avg(item_auctions):
    ignored_prices = {
        "Aspect of the Dragons": 2400000,
        "Old Dragon Chestplate": 540000,
        "Treecapitator": 2200000,
        "Aspect of the Void": 5200000,
        "Bonzo's Mask": 1000000,
        "Treecapitator": 2500000,
        "Aspect of the Jerry, Signature Edition": 0,


    }
    
    
    prices = [auction["price"] for auction in item_auctions.values()]
    sorted_prices = sorted(prices)

    if any(item_key in ignored_prices for item_key in item_auctions.keys()):
        return ignored_prices[item_auctions]
    
    elif int(len(prices)/5+1) < len(prices)-1:
        tailprices = sorted_prices[:int(len(prices)/5+1)]
        return sum(tailprices) / len(tailprices)
    else:
        return sum(prices) / len(prices)


setprices = {
        "Treecapitator": 2000000,
        "Aspect of the Void": 4500000,
        "Bonzo's Mask": 500000,
        "Scarf's Studies": 10000000,
        "Hyperion" :100000000,
        "Bonzo's Staff": 3000000,
        "Storm's":1000000,
        "Necron's":1000000,
        "Frozen Scythe": 6000000,
        "Giant's Sword": 10000000,
        "Spirit Sceptre": 2000000,
        "Melon Dicer 3.0": 9000000,
        "Juju Shortbow": 15000000,
        "Dark Claymore": 10000000,
        "Frozen Scythe": 5000000,
        "Pigman Sword":9000000,
        "Vorpal Katana":3000000,
        "Atomsplit Katana":80000000,
        "Bonemerang":10000000,
        "Daedalus Axe": 16000000,
        "Felthorn Reaper": 20000000,
        "Terminator": 500000000,
        "Axe of the Shredded": 40000000,
        "Reaper Scythe": 12000000,
        "Glacial Scythe": 25000000,
        "of Divan": 15000000,
        "Scylla": 99000000,
        "Valkyrie": 99000000,
        "Astraea": 99000000,
        "Necron's Handle": 99000000,
        "Judgement Core": 99000000,
        "Divan's Alloy": 99000000,
        "Divan's Drill": 99000000,
        "Golden Dragon": 99000000,
        "Ender Dragon": 99000000,
        "Artifact of Control": 99000000,
        "Hegemony Artifact": 99000000,
        "Pandora's Box": 99000000,
        "Bingo Relic": 99000000,
        "Rift Prism": 99000000,
        "Bingo Artifact": 99000000,
        "Shen's Regalia": 99000000,
        "Ender Relic": 99000000,
        "Inferno Kuudra Core": 99000000,
        "Warden Helmet": 99000000,
        "Warden Heart": 99000000,
        "Plasmaflux Power Orb": 99000000,
        "Plasma Nucleus": 99000000,
        "Diamond Necron Head": 99000000,
        "Shattered Locket": 99000000,
        "Titanium Drill DR-x655": 99000000






    }

reforgenames = {
"Absolutely ",
"Ambered ",
"Ancient ",
"Auspicious ",
"Awkward ",
"Blessed ",
"Blood-Soaked ",
"Blooming ",
"Bountiful ",
"Bulky ",
"Bustling ",
"Candied ",
"Chomp ",
"Clean ",
"Coldfused ",
"Cubic ",
"Deadly ",
"Dirty ",
"Double-Bit ",
"Earthy ",
"Empowered ",
"Epic ",
"Even More ",
"Excellent ",
"Extremely ",
"Fabled ",
"Fair ",
"Fanged ",
"Fast ",
"Festive ",
"Fierce ",
"Fine ",
"Fleet ",
"Fortified ",
"Fortunate ",
"Fruitful ",
"Gentle ",
"Giant ",
"Gilded ",
"Glistening ",
"Grand ",
"Great ",
"Green Thumb ",
"Hasty ",
"Headstrong ",
"Heated ",
"Heavy ",
"Heroic ",
"Highly ",
"Hyper ",
"Jaded ",
"Jerry's ",
"Legendary ",
"Light ",
"Loving ",
"Lucky ",
"Lumberjack's ",
"Lush ",
"Magnetic ",
"Mithraic ",
"Moil ",
"Mossy ",
"Mythic ",
"Neat ",
"Necrotic ",
"Not So ",
"Odd ",
"Peasant's ",
"Perfect ",
"Pitchin' ",
"Precise ",
"Prospector's ",
"Pure ",
"Rapid ",
"Refined ",
"Renowned ",
"Rich ",
"Ridiculous ",
"Robust ",
"Rooted ",
"Rugged ",
"Salty ",
"Sharp ",
"Smart ",
"Snowy ",
"Spicy ",
"Spiked ",
"Spiritual ",
"Stellar ",
"Stiff ",
"Strengthened ",
"Sturdy ",
"Submerged ",
"Suspicious ",
"Thicc ",
"Titanic ",
"Toil ",
"Treacherous ",
"Undead ",
"Unreal ",
"Unyielding ",
"Very ",
"Warped ",
"Waxed ",
"Wise ",
"Withered ",
"Zooming ",
"✪✪✪✪✪",
"✪✪✪✪",
"✪✪✪",
"✪✪",
"✪",
"➊",
"➋",
"➌",
"➍",
"➎",
"⚚ ",

}

emojilist = {
    "COMMON":"⚪",
    "UNCOMMON":"🟢",
    "RARE":"🔵",
    "EPIC":"🟣",
    "LEGENDARY":"🟡",
    "MYTHIC":"🩷",
    "SPEICAL":"🔴",
    "VERY_SPECIAL":"🔴",
}

def findsetval(itemname):
    global setprices
    
    for key in setprices.keys():
        if key in itemname:
            return setprices[key]
    return 0

# def valueofreforge(reforgename):
#     stdreforgeprices = {
#         "Necrotic": 2400000,
#         "Loving": 540000,
#         "Jaded": 2200000,

#     }
#     if any(reforgename in stdreforgeprices for reforgename in reforgename.keys()):
#         return stdreforgeprices[reforgename]
output = []

firsttimerefresh=True

def cleanthename(dirtyitemname):
    global reforgenames
    cleanname = dirtyitemname
    for name in reforgenames:
        if name in cleanname:
            cleanname= cleanname.replace(name, "")
    return cleanname.rstrip()

def nowlowestbin(itemname):
    global lbinlist
    global setprices
    global uuidlist
    totalpages = requests.get(f"https://api.hypixel.net/skyblock/auctions").json()["totalPages"]
    lbin = 9999999999999
    for i in range(0, totalpages):
        data = requests.get(f"https://api.hypixel.net/skyblock/auctions?page={i}&key={API_KEY}").json()
        
        for auction in data["auctions"]:
            try:
                if auction.get("bin"):
                    item_name = auction["item_name"]
                    if itemname in item_name and auction["starting_bid"]<lbin:
                        lbin = auction["starting_bid"]
            except KeyError:
                pass

    save_data(lbinlist,"lbinlist.json")
    return lbin

def recalcalllbins():
    global lbinlist
    global setprices
    global uuidlist
    totalpages = requests.get(f"https://api.hypixel.net/skyblock/auctions").json()["totalPages"]
    lbin = 9999999999999
    for i in range(0, totalpages):
        data = requests.get(f"https://api.hypixel.net/skyblock/auctions?page={i}&key={API_KEY}").json()
        
        for auction in data["auctions"]:
            try:
                if auction.get("bin"):
                    item_name = cleanthename(auction["item_name"])
                    if item_name in lbinlist:
                        if auction["starting_bid"]<lbinlist[item_name]:
                            lbinlist[item_name] = auction["starting_bid"]
                    elif auction["starting_bid"]<lbin:
                        lbinlist[item_name] = {}
                        lbinlist[item_name] = auction["starting_bid"]

            except KeyError:
                pass
    save_data(lbinlist,"lbinlist.json")

def findrecomb():
    global setprices
    global uuidlist
    for i in range(0, 50):
        data = requests.get(f"https://api.hypixel.net/skyblock/auctions?page={i}&key={API_KEY}").json()
        for auction in data["auctions"]:
            try:
                if auction.get("bin") and auction.get("category") in ["armor", "weapon"]:
                    item_name = auction["item_name"]
                    uuid = auction["uuid"]
                    item_lore = auction["item_lore"]
                    if uuid not in uuidlist:
                        if "§ka" in item_lore and auction["starting_bid"] <500000 :
                            output.append((item_name, f"{auction["starting_bid"]:,}", f"{auction["starting_bid"]:,}", auction["tier"], uuid, " ","0"))
                            print((item_name, f"{auction["starting_bid"]:,}", f"{auction["starting_bid"]:,}", auction["tier"], uuid, " ","0"))
                        uuidlist[uuid]= True;
            except KeyError:
                pass

def bettergetmyguy():
    global setprices
    global uuidlist
    for i in range(0, 1):
        data = requests.get(f"https://api.hypixel.net/skyblock/auctions?page={i}&key={API_KEY}").json()
        for auction in data["auctions"]:
            try:
                if auction.get("bin"): #and auction.get("category") in ["armor", "weapon"]:
                    item_name = auction["item_name"]
                    uuid = auction["uuid"]
                    if uuid not in uuidlist:
                        for name in setprices:
                            if name in item_name:
                                profit = int(findsetval(item_name) - auction["starting_bid"])
                                if profit > 0 and not any(name in item_name for name in FILTERED_ITEM_NAMES) and 0< auction["starting_bid"]<6000000000:
                                    output.append((item_name, f"{auction["starting_bid"]:,}", f"{profit:,}", auction["tier"], uuid, name,"0"))
                                elif profit > -10000000000 and not any(name in item_name for name in FILTERED_ITEM_NAMES) and 0< auction["starting_bid"]<6000000000:
                                    output.append((item_name, f"{auction["starting_bid"]:,}", f"{profit:,}", auction["tier"], uuid, name,"1"))
                            uuidlist[uuid]= True;
            except KeyError:
                pass

def biddingauctions():
    global setprices
    global uuidlist
    global lbinlist
    headers = {
    "Title": "💸 very big very many moneys 💸".encode(encoding='utf-8'),
    "Priority": "high",
    # "Tags": "warning,cd",
    }
    for i in range(0, 1):
        data = requests.get(f"https://api.hypixel.net/skyblock/auctions?page={i}&key={API_KEY}").json()
        for auction in data["auctions"]:
            try:
                if not auction.get("bin") or auction.get("category") in ["misc"]:
                    item_name = auction["item_name"]
                    cleanname = cleanthename(auction["item_name"])
                    uuid = auction["uuid"]
                    end = auction["end"]
                    timenow = int(time.time())*1000
                    rarityicon = emojilist[auction["tier"]]
                    if lbinlist[cleanname] >1000000 or auction.get("category") in ["misc"]:
                        if uuid not in uuidlist:
                            if end - timenow <7*60*1000:
                                if auction["bids"]:
                                    if max(auction["bids"], key=lambda x: x["amount"])["amount"]<lbinlist[cleanname]-2000000:
                                        banana = "1"
                                        one = rarityicon + " " +  item_name + " " +  rarityicon
                                        # two = "Current bid: "+ f"{max(auction["bids"], key=lambda x: x["amount"])["amount"]:,}"
                                        # three = str(datetime.fromtimestamp(end/1000).strftime("%I:%M %p") + " --- "+ str(int((time.time()*1000-end)/-60000))+ " min remaining" + " --- +"+ f"{-max(auction["bids"], key=lambda x: x["amount"])["amount"]+lbinlist[cleanname]:,}")
                                        # message = (one+"\n"+two+"\n"+three).encode(encoding='utf-8')
                                        if -max(auction["bids"], key=lambda x: x["amount"])["amount"]+lbinlist[cleanname] >4000000:
                                            pygame.mixer.music.play()
                                            # requests.post("https://ntfy.sh/skypixelhyblocktest", data=message, headers=headers)
                                            banana = "0"
                                        output.append((item_name,"Current bid: "+f"{max(auction["bids"], key=lambda x: x["amount"])["amount"]:,}",datetime.fromtimestamp(end/1000).strftime("%I:%M %p") + " ----- "+ str(int((time.time()*1000-end)/-60000))+ " min remaining"+ " ----- +"+ f"{-max(auction["bids"], key=lambda x: x["amount"])["amount"]+lbinlist[cleanname]:,}", auction["tier"], uuid, cleanname,banana))
                                elif not auction["bids"]:
                                    if auction["starting_bid"]<lbinlist[cleanname]:
                                        banana="1"
                                        one = rarityicon + " " +  item_name + " " + rarityicon
                                        # two = "Current bid: "+ f"{max(auction["bids"], key=lambda x: x["amount"])["amount"]:,}"
                                        # three = str(datetime.fromtimestamp(end/1000).strftime("%I:%M %p") + " --- "+ str(int((time.time()*1000-end)/-60000))+ " min remaining" + " --- +"+ f"{-max(auction["bids"], key=lambda x: x["amount"])["amount"]+lbinlist[cleanname]:,}")
                                        # # message = (one+"\n"+two+"\n"+three).encode(encoding='utf-8')
                                        if -auction["starting_bid"]+lbinlist[cleanname]>4000000:
                                            pygame.mixer.music.play()
                                            # requests.post("https://ntfy.sh/skypixelhyblocktest", data=message, headers=headers)
                                            banana = "0"
                                        output.append((item_name,"Current bid: "+f"{auction["starting_bid"]:,}",datetime.fromtimestamp(end/1000).strftime("%I:%M %p") + " ----- "+ str(int((time.time()*1000-end)/-60000))+ " min remaining"+ " ----- +"+ f"{-auction["starting_bid"]+lbinlist[cleanname]:,}", auction["tier"], uuid, cleanname,banana))
            except KeyError:
                pass


    save_data(uuidlist,"uuidlist.json")

    

async def asleep():
    global firsttimerefresh
    global sillycounter
    duration = await get_duration_until_api_update()
    
    # asyncio.sleep(duration)
    
    # bettergetmyguy()
    # findrecomb()
    if firsttimerefresh or sillycounter>=5:
        #check if it iBECOMES OFF EVERY LIKE 10 DO A COUNTERs the first ever sleep, otheriwase delay by 60
        print(f"NextAPIupdate in: {duration}")
        print("First:" + str(sillycounter))
        biddingauctions()
        sillycounter = 0
        time.sleep(duration)
        firsttimerefresh = False
    elif not firsttimerefresh:   
        print(f"Next API update in: {duration}")
        print("This is:" + str(sillycounter))
        biddingauctions()
        sillycounter+=1
        time.sleep(60)

def fetch_data():
    global output
    while True:
        
        asyncio.run(asleep()) #mnually sync the refresh set to 1 min to the calling.


@app.route('/')
def index():
    return render_template('index.html', output_data=output)


data_thread = threading.Thread(target=fetch_data)
data_thread.daemon = True
data_thread.start()


if __name__ == '__main__':
    # recalcalllbins()
    app.run(debug=True)
    

    #figure out how to self host thishkjashdkjashd #figure out how to self host thishkjashdkjashd

