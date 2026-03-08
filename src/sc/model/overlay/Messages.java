package sc.model.overlay;

public class Messages {
    MessageConfig rushItemConfig = new MessageConfig(
            "Ice Rush (T1~T3)",
            "Temporary boost for your ice gathering.",
            "Causes screen shake and massive gains.",
            "Cooldown applies after use.",
            "Keep an eye on the end tick!",
            MessageKey.key.rushItem
    );

    // 진공청소기 설명
    MessageConfig vacuumConfig = new MessageConfig(
            "Ice Vacuum",
            "Sucks up all ice within 1.5 seconds.",
            "Costs 600 coins. Use it wisely.",
            "Sound: 184312__swiftoid... (Floppy Sound)",
            "Cooldown: 720 ticks (Adjustable).",
            MessageKey.key.vacuum
    );

    // 자동 수집 설명
    MessageConfig autoCollectConfig = new MessageConfig(
            "Auto Collect Upgrade",
            "Permanent upgrade for lazy developers.",
            "Max Level: 4. Final cost: 100,000.",
            "Let the system do the work for you.",
            "Pricey, but worth every coin.",
            MessageKey.key.autoCollect
    );

    MessageConfig frozenFieldConfig = new MessageConfig(
            "❄ Frozen Field",
            "The main stage of your adventure.",
            "Watch the sky! Resources are falling.",
            "Higher skills bring better ice drops.",
            "Keep the field clean by collecting all!",
            MessageKey.key.frozenFiled
    );
    MessageConfig levelConfig = new MessageConfig(
            "Level & Experience",
            "Current Level: Up to 32.",
            "Earn Skill Points by gathering XP.",
            "The grind gets harder as you go.",
            "Next milestone: Check your XP bar.",
            MessageKey.key.level
    );

    // 스폰 확률 강화 설명
    MessageConfig spawnSkillConfig = new MessageConfig(
            "Ice Spawn Mastery",
            "Permanently increase spawn rates.",
            "Supports Tier 1, 2, and 3.",
            "More legendary ice, more money.",
            "Max level for each tier is 4.",
            MessageKey.key.iceSpawnChance
    );

    MessageConfig clickOffsetConfig = new MessageConfig(
            "Click Offset Mastery",
            "Expand your reach, ease your fingers.",
            "Higher level = Larger click recognition.",
            "Up to 10 levels of convenience.",
            "Don't click harder, click smarter.",
            MessageKey.key.clickOffset
    );

    // 쿨타임 단축 스킬 설명
    MessageConfig cooldownSkillConfig = new MessageConfig(
            "Time Manipulation",
            "Reduces item cooldowns significantly.",
            "Level 10: 1.0f -> 0.32f multiplier.",
            "Rush items can be used more often.",
            "Your MacBook will scream in joy.",
            MessageKey.key.itemCoolTime
    );
    MessageConfig knobSystemConfig = new MessageConfig(
            "Analog Knob UI",
            "Rotate with Mouse Wheel.",
            "Hold SHIFT for precise control.",
            "Range: 270 degrees (7:30 to 4:30).",
            "Built with AffineTransform & Trigonometry.",
            MessageKey.key.knob
    );
    MessageConfig longTimeQuestConfig = new MessageConfig(
            "The Long Way Home",
            "Goal: Collect 5,000 Basic Ice.",
            "This is not a sprint, it's a marathon.",
            "Reward: 50,000 Coins & 4,000 XP.",
            "Bonus: Ice Collect Count +5 permanently.",
            MessageKey.key.LongTimeQuest
    );

    MessageConfig questGeneralConfig = new MessageConfig(
            "Daily Missions",
            "Small tasks for quick rewards.",
            "Refresh cost: 400 Coins.",
            "Check your progress in the UI.",
            "Don't forget to claim your rewards!",
            MessageKey.key.Quest
    );

    MessageConfig questRefreshConfig = new MessageConfig(
            "♻ Quest Refresh",
            "Don't like your current missions?",
            "Pay 400 Coins to roll new ones.",
            "Resets progress for Slot 1 & 2.",
            "Good luck with the next RNG!",
            MessageKey.key.refresh
    );
    MessageConfig quitMessage = new MessageConfig(
            "⚠ Quit Game",
            "Are you sure you want to leave?",
            "All unsaved progress will be lost in the void.",
            "The outside world is scary...",
            "Come back soon!",
            MessageKey.key.quit
    );

    MessageConfig saveConfig = new MessageConfig(
            "▼ Save Game",
            "Recording your current reality.",
            "Writing bytes to the disk...",
            "Don't turn off your MacBook.",
            "Safe and sound!",
            MessageKey.key.save
    );

    MessageConfig loadConfig = new MessageConfig(
            "▲ Load Game",
            "Traveling back in time.",
            "Restoring your previous soul.",
            "Did you regret your choices?",
            "Wait for the butterfly effect.",
            MessageKey.key.load
    );

    MessageConfig pauseConfig = new MessageConfig(
            "⏸ Game Paused",
            "Time has stopped by your command.",
            "Take a deep breath. Drink some water.",
            "The single thread is resting now.",
            "Press again to resume reality.",
            MessageKey.key.pause
    );

    MessageConfig settingConfig = new MessageConfig(
            "⚙ Settings",
            "Adjusting the universe parameters.",
            "Fine-tune your experience.",
            "Enable 'Nonsense' for more fun.",
            "Customizing AWT to its limit.",
            MessageKey.key.setting
    );

    MessageConfig consoleConfig = new MessageConfig(
            ">_ Developer Console",
            "Entering the Matrix...",
            "Written in Kotlin, powered by Magic.",
            "Be careful with the commands.",
            "sudo rm -rf / (Just kidding!)",
            MessageKey.key.console
    );

    MessageConfig barInfoConfig = new MessageConfig(
            "Top Navigation Bar",
            "This is the 'Scope' framework's core UI.",
            "It responds to your mouse movement (y <= 40).",
            "Fixed if game is paused or setting is on.",
            "Pure AWT JPanel manual rendering.",
            MessageKey.key.bar
    );

    MessageConfig nonsenseConfig = new MessageConfig(
            "Nonsense Area (Easter Egg)",
            "The center of the bar displays random truths.",
            "From F1 memes to broken English...",
            "It changes every 480 ticks (8 seconds).",
            "Can be toggled in UI Settings.",
            MessageKey.key.nonsense
    );

    MessageConfig statusLightConfig = new MessageConfig(
            "System Status Light",
            "The small circle on the far right (x=1890).",
            "Green: Engine is running. Play hard.",
            "Red: System is paused. Time to think.",
            "It reflects 'iPause.isPause()' state.",
            MessageKey.key.pauseLight
    );
    MessageConfig economyConfig = new MessageConfig(
            "Economy & Growth",
            "Coin: Buy items and upgrades in Shop.",
            "XP: Earned from ice and missions.",
            "Level: Gain Skill Points to buff stats.",
            "The more you collect, the stronger you get.",
            MessageKey.key.economy
    );

}
