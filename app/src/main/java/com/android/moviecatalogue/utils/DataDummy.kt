package com.android.moviecatalogue.utils

import com.android.moviecatalogue.data.source.local.entity.DataEntity
import com.android.moviecatalogue.data.source.local.entity.MovieTvEntity
import com.android.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.android.moviecatalogue.data.source.remote.response.DetailTvShowResponse
import com.android.moviecatalogue.data.source.remote.response.Genre

object DataDummy {
    fun generateDataDummyMovies(): List<DataEntity>{
        val movie = ArrayList<DataEntity>()

        movie.add(
            DataEntity(1,
            "Dilwale Dulhania Le Jayenge",
            listOf(Genre("Comedy, Drama, Romance")),
            "Raj is a rich, carefree, happy-go-lucky second generation NRI. Simran is the daughter of Chaudhary Baldev Singh, who in spite of being an NRI is very strict about adherence to Indian values. Simran has left for India to be married to her childhood fiancé. Raj leaves for India with a mission at his hands, to claim his lady love under the noses of her whole family. Thus begins a saga.",
            "1995-10-20",
            8.7,
                "Released",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/2CAL2433ZeIihfX1Hb2139CX0pW.jpg")
        )

        movie.add(
            DataEntity(2,
            "Zack Snyder\'s Justice League",
            listOf(Genre("Action, Adventure, Fantasy, Science Fiction")),
            "Determined to ensure Superman\'s ultimate sacrifice was not in vain, Bruce Wayne aligns forces with Diana Prince with plans to recruit a team of metahumans to protect the world from an approaching threat of catastrophic proportions.",
            "Mar 18, 2021",
            8.6,
                status = "Released",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/tnAuB8q5vv7Ax9UAEje5Xi4BXik.jpg")
        )

        movie.add(
            DataEntity(3,
            "Chaos Walking",
            listOf(Genre("Science Fiction, Action, Adventure, Thriller")),
            "Two unlikely companions embark on a perilous adventure through the badlands of an unexplored planet as they try to escape a dangerous and disorienting reality, where all inner thoughts are seen and heard by everyone.",
            "Feb 24, 2021",
            7.5,
                "Released",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/9kg73Mg8WJKlB9Y2SAJzeDKAnuB.jpg")
        )

        movie.add(
            DataEntity(4,
            "Raya and the Last Dragon",
            listOf(Genre("Animation, Adventure, Fantasy, Family, Action")),
            "Long ago, in the fantasy world of Kumandra, humans and dragons lived together in harmony. But when an evil force threatened the land, the dragons sacrificed themselves to save humanity. Now, 500 years later, that same evil has returned and it\'s up to a lone warrior, Raya, to track down the legendary last dragon to restore the fractured land and its divided people.",
            "Mar 03, 2021",
            8.3, "Released",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/lPsD10PP4rgUGiGR4CCXA6iY0QQ.jpg")
        )

        movie.add(
            DataEntity(5,
            "Tom & Jerry",
            listOf(Genre("Comedy, Family, Animation")),
            "Tom the cat and Jerry the mouse get kicked out of their home and relocate to a fancy New York hotel, where a scrappy employee named Kayla will lose her job if she can\'t evict Jerry before a high-class wedding at the hotel. Her solution? Hiring Tom to get rid of the pesky mouse",
            "Feb 11, 2021",
            7.3,"Released",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/6KErczPBROQty7QoIsaa6wJYXZi.jpg")
        )

        movie.add(
            DataEntity(6,
            "Monster Hunter",
            listOf(Genre("Fantasy, Action, Adventure")),
            "A portal transports Cpt. Artemis and an elite unit of soldiers to a strange world where powerful monsters rule with deadly ferocity. Faced with relentless danger, the team encounters a mysterious hunter who may be their only hope to find a way home.",
            "Dec 03, 2020",
            7.1,"Released",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/1UCOF11QCw8kcqvce8LKOO6pimh.jpg")
        )

        movie.add(
            DataEntity(7,
            "Cherry",
            listOf(Genre("Crime, Drama")),
            "Cherry drifts from college dropout to army medic in Iraq - anchored only by his true love, Emily. But after returning from the war with PTSD, his life spirals into drugs and crime as he struggles to find his place in the world.",
            "Feb 26, 2021",
            7.6,"Released",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/pwDvkDyaHEU9V7cApQhbcSJMG1w.jpg")
        )

        movie.add(
            DataEntity(8,
            "Wonder Woman 1984",
            listOf(Genre("Fantasy, Action, Adventure")),
            "A botched store robbery places Wonder Woman in a global battle against a powerful and mysterious ancient force that puts her powers in jeopardy.",
            "Dec 16, 2020",
            6.8,"Released",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg")
        )

        movie.add(
            DataEntity(9,
            "Sentinelle",
            listOf(Genre("Thriller, Action, Drama")),
            "Transferred home after a traumatizing combat mission, a highly trained French soldier uses her lethal skills to hunt down the man who hurt her sister.",
            "Mar 05, 2021",
            6.1, "Released",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/fFRq98cW9lTo6di2o4lK1qUAWaN.jpg")
        )

        movie.add(
            DataEntity(10,
            "Bellow Zero",
            listOf(Genre("Action, Crime, Thriller")),
            "When a prisoner transfer van is attacked, the cop in charge must fight those inside and outside while dealing with a silent foe: the icy temperatures.",
            "Jan 29, 2021",
            6.4,"Released",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/dWSnsAGTfc8U27bWsy2RfwZs0Bs.jpg")
        )
        return movie
    }

    fun generateDataDummyTvShows() : List<DataEntity>{
        val tvShow = ArrayList<DataEntity>()

        tvShow.add(
            DataEntity(1,
            "I Am Not an Animal",
            listOf(Genre("Animation, Comedy")),
            "I Am Not An Animal is an animated comedy series about the only six talking animals in the world, whose cosseted existence in a vivisection unit is turned upside down when they are liberated by animal rights activists.",
            "2004-05-10",
            9.4,"Ended",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/qG59J1Q7rpBc1dvku4azbzcqo8h.jpg")
        )

        tvShow.add(
            DataEntity(2,
            "My Hero Academia",
            listOf(Genre("Sci-Fi & Fantasy, Action & Adventure, Comedy, Animation")),
            "In a world where eighty percent of the population has some kind of super-powered Quirk, Izuku was unlucky enough to be born completely normal. But that won’t stop him from enrolling in a prestigious hero academy. Now, he\'ll get his first taste of brutal rivalry from other schools as he braves the cutthroat, no-holds-barred provisional license exam.",
            "Apr 03, 2016",
            8.9,"Aired",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/phuYuzqWW9ru8EA3HVjE9W2Rr3M.jpg")
        )

        tvShow.add(
            DataEntity(3,
                "Dragon Quest: The Adventure of Dai",
                listOf(Genre("Animation, Action & Adventure, Sci-Fi & Fantasy")),
                "A long time ago, there was a valiant swordsman who came to be known simply as \"the hero.\" There was a demon who has caused people suffering. The hero and his companions arrived to challenge the demon to a battle and by combining their powers, the battle was brought swift conclusion. With no one around to cause trouble, the island became a quiet place where everyone could live together in peace. Several years later, the demon is revived. Our present-day protagonist, Dai, lives on a remote island in the southern seas and dreams of becoming a great hero. When he hears about the demon's revival, Dai and his friends take it upon themselves to stop him and the evil force that revived him. Along the way, Dai discovers the identity of \"the hero,\" the truth behind the evil force who revived the demon, and Dai's own hidden powers that surface in times of peril.",
                "Oct 02, 2020",
                6.8, "Ended",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/i28TVlLozR09hqkGsSlu5wa1X2J.jpg")
        )

        tvShow.add(
            DataEntity(4,
                "Vivy -Fluorite Eye's Song",
                listOf(Genre("Animation, Drama, Sci-Fi & Fantasy")),
                "In the near future, Vivy, a diva-type A.I., went up on stage each day with hopes of putting her heart into her song. One day, the A.I. Matsumoto, who claims to have arrived from 100 years in the future, appears before Vivy with an important request..",
                "Apr 03, 2021",
                8.0, "Ended",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/nJnatRZUXAS6I5MpBaBO5T1W8Ca.jpg")
        )

        tvShow.add(
            DataEntity(5,
                "I've Been Killing Slimes for 300 Years and Maxed Out My Level",
                listOf(Genre    ("Animation, Comedy, Sci-Fi & Fantasy")),
                "After dying of overwork in the real world, I’m reincarnated as an immortal witch, and I spend 300 years enjoying a relaxing life. At some point, though, I end up at level 99! All those years spent killing slimes to make the money to pay the bills gave me a ton of experience points. Rumors of the level 99 witch spread, and soon I’m up to my ears in curious adventurers, duelist dragons, and even a monster girl calling me her mom! I’ve never been on an adventure, but I’m the strongest in the world… What’s going to happen to my relaxing life?!",
                "Apr 10, 2021",
                8.3, "Aired",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/l1I63ZAwUmCOH6PS7EfUb9DwoMz.jpg")
        )

        tvShow.add(
            DataEntity(6,
                "The World Ends With You: The Animation",
                listOf(Genre("Animation, Drama, Mystery, Action & Adventure")),
                "Neku awakens in the middle of Shibuya's bustling Scramble Crossing with no memory of how he got there. Little does he know, he's been transported to an alternate plane of existence known as the Underground. Now an unwilling participant in the mysterious \"Reapers' Game,\" Neku must partner up with a girl named Shiki in order to survive...",
                "Apr 10, 2021",
                null, "Ended",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/9Ei08FZjGOp3wwQGWAM8vtEJn3C.jpg")
        )

        tvShow.add(
            DataEntity(7,
                "Final Space",
                listOf(Genre("Animation, Action & Adventure, Sci-Fi & Fantasy, Comedy")),
                "An astronaut named Gary and his planet-destroying sidekick Mooncake embark on serialized journeys through space in order to unlock the mystery of “Final Space,” the last point in the universe, if it actually does exist.",
                "Feb 26, 2018",
                8.6, "Ended",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/zT6tXWsJoBOWJT1PeUEKLTFOoHh.jpg")
        )

        tvShow.add(
            DataEntity(8,
                "Amphibia",
                listOf(Genre("Animation, Comedy, Action & Adventure, Sci-Fi & Fantasy")),
                "The adventures of 13-year-old, self-centered Anne Boonchuy who is magically transported to the fictitious world of Amphibia, a rural marshland full of frog-people. With the help of an excitable young frog named Sprig, Anne will transform into a hero and discover the first true friendship of her life.",
                "Jun 17, 2019",
                7.7, "Ended",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/4eQizSNtiXLCBFyWiRivjaSw52j.jpg")
        )

        tvShow.add(
            DataEntity(9,
                "Yuru Camp △ Live Action",
                listOf(Genre("Comedy")),
                "This is the story of a winter day. Kagamihara Nadeshiko, a female high school student who moved from Shizuoka to Yamanashi, rode a bicycle to see Mt. Fuji, but unfortunately, the weather was cloudy and Mt. Fuji cannot be seen. Tired, Nadeshiko falls asleep on the spot and wakes up at night. This is her first time going there and she didn't know how to return.\n" +
                        "\n" +
                        "Fortunately, Shima Rin, a girl who loves camping, saves her. They lit a bonfire to warm up and the sound of the blazing firewood permeates the silence of the lake.",
                "Jan 09, 2020",
                7.5, "Ended",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/gJDaabuOo6Gah2u23E9qI4z8eIY.jpg")
        )

        tvShow.add(
            DataEntity(10,
                "Back Arrow",
                listOf(Genre("Animation, Action & Adventure, Sci-Fi & Fantasy")),
                "Lingalind is a land enclosed by the Wall. The Wall covers, protects, cultivates, and nurtures the land. One day in Edger, a village on the outskirts of Lingalind, a mysterious man named Back Arrow appears. Arrow has lost his memory, but he claims that all he knows is that “I came from outside the Wall.”",
                "",
                8.0,"Aired",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/w9MVFImrsQ4YkjG1bNGhNGulWwI.jpg")
        )


        return tvShow
    }

    fun generateDummyMovies(): List<MovieTvEntity>{
        val movie = ArrayList<MovieTvEntity>()

        movie.add(
            MovieTvEntity(id = 1,
                title = "Dilwale Dulhania Le Jayenge",
                releaseDate = "1995-10-20",
                voteAverage = 8.7,
                type = "Movie",
                posterPath = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/2CAL2433ZeIihfX1Hb2139CX0pW.jpg")
        )

        return movie
    }

    fun generateDummyTvShows() : List<MovieTvEntity>{
        val tvShow = ArrayList<MovieTvEntity>()

        tvShow.add(
            MovieTvEntity(id = 1,
                title = "I Am Not an Animal",
                releaseDate = "2004-05-10",
                voteAverage = 9.4,
                type = "TvShow",
                posterPath = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/qG59J1Q7rpBc1dvku4azbzcqo8h.jpg")
        )

        return tvShow
    }

    val dummyDetailMovies = DetailMovieResponse(
        listOf(Genre("Comedy, Drama, Romance")),
        1,
        "Raj is a rich, carefree, happy-go-lucky second generation NRI. Simran is the daughter of Chaudhary Baldev Singh, who in spite of being an NRI is very strict about adherence to Indian values. Simran has left for India to be married to her childhood fiancé. Raj leaves for India with a mission at his hands, to claim his lady love under the noses of her whole family. Thus begins a saga.",
        "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/2CAL2433ZeIihfX1Hb2139CX0pW.jpg",
        "1995-10-20",
        "Released",
        "Dilwale Dulhania Le Jayenge",
        8.7
    )

    val dummyDetailTvShow = DetailTvShowResponse(
        "2004-05-10",
        listOf(Genre("Animation, Comedy")),
        1,
        "I Am Not an Animal",
        "I Am Not An Animal is an animated comedy series about the only six talking animals in the world, whose cosseted existence in a vivisection unit is turned upside down when they are liberated by animal rights activists.",
        "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/qG59J1Q7rpBc1dvku4azbzcqo8h.jpg",
        "Ended",
        9.4
    )
}