import {api} from "@/api/api";

export type QuestionDto = {
    id: number;
    study: string;
    description: string;
    answers: string[];
    trueAnswers: string[];
    type: string;
};

export async function allQuestions(): Promise<QuestionDto[]> {
    const response = await api.get("/questions");

    return response.data;
}