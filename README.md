# Fifth Assignment: Multithreading Basics 🎮

## Introduction

## Why Learn ? 📊

## Prerequisites ✅

Before diving into this project, make sure you have the following tools and knowledge:

### Tools Needed:

- **Java 17 or higher** : Download the latest version from [Oracle's Java website](https://www.oracle.com/java/technologies/downloads/).
- **Git** : Install Git, a powerful version control system, from [Git's official website](https://git-scm.com/downloads).
- **Gradle** : Utilize Gradle (recommended version 7.6 or newer) as the build tool. You can find installation instructions [here](https://gradle.org/install/).


## Objectives 🎯

## Theoretical Questions 📝

## Practical Questions 💻

### Typing Test
#### Task Description
1.  Show words to the user one at a time, and set a timeout for each word.

2.  If the user does not type the word within the timeout, the chance is lost and the app moves to the next word.

3.  Input must be available at all times — the timeout should not wait for the user to enter something.

4.  If the user types a word correctly or incorrectly before timeout, the program must immediately show the result and move to the next word, ignoring any remaining time.

#### 🛠 What  You  Need to Do

- You can change the provided code  if you have your own solution.
- However, it is recommended to keep the current structure for consistency and guidance.
- Currently, some test words are hardcoded; instead, you must:
  - Read words from the provided file containing 100 words.
  - Each time, randomly select words from this list for testing.
- After the test is completed:
  - Show the total number of correct and incorrect words.
  - Show the total time taken.
  - Show the average time per word.
-  Timeout duration is not constant. 
  - You must calculate the timeout dynamically, for example:
    - Based on the length of the word,
    - Or using any logic you choose.

### Report Generator

#### Task Description
This Java project reads and processes multiple order files concurrently using multithreading. Each thread analyzes one file, calculates statistics such as total cost, number of products purchased, average discount, and identifies the most expensive purchase after discount. Product data is preloaded from a shared catalog. The goal is to demonstrate efficient file processing using threads while safely accessing shared resources.

#### 🛠 What  You  Need to Do

- Read product data from a Products.txt file and populate a shared product catalog.
- Create a thread (TaskRunnable) for each order file to:
  - Read and parse the file line by line.
  - Accumulate statistics (total cost, total items, average discount, most expensive purchase).
- Use multithreading to process multiple order files in parallel.
- Generate a report for each file, showing:
  - Total cost
  - Total items bought
  - Average discount
  - Most expensive purchase after discount
---



## Bonus Objectives🌟

## Notes 📝

## Evaluation 🧐

## Submission 📁

To share your work:

1. Add your mentor as a contributor to the project.
2. Create a `develop` branch for implementing features.
3. Use Git for regular code commits.
4. Push your code to the remote repository.
5. Submit a pull request to merge the `develop` branch with `main`.

The deadline for submitting your code is ...

If you have any further questions or need clarification, do not hesitate to reach out to your mentor. Good luck with your project! 🌟

## Resources 📚
