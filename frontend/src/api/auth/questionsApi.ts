import {api} from "@/api/api";

export type QuestionDto = {
    id: number;
    study: string;
    description: string;
    answers: string[];
    trueAnswers: number[];
    type: string;
};

export async function allQuestions(): Promise<QuestionDto[]> {
    const response = await api.get("/questions");

    return response.data;
}

export async function createTest(studyId: number, count: string): Promise<QuestionDto[]> {
    const response = await api.get("/studies/" + studyId + "/generate", {
        params: JSON.parse(count)
    });

    return response.data;
}

export async function createQuestion(studyId: number, type: string, description: string, answers: string[], correctAnswers: number[]): Promise<void> {
    const response = await api.post("/studies/" + studyId + "/add", {
        description: description,
        answers: answers,
        trueAnswers: correctAnswers,
        type: type
    });
}

